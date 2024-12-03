package com.lcaohoanq.shoppe.services.auth;

import com.lcaohoanq.shoppe.components.JwtTokenUtils;
import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.constants.Regex;
import com.lcaohoanq.shoppe.dtos.UserRegisterDTO;
import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.exceptions.ExpiredTokenException;
import com.lcaohoanq.shoppe.exceptions.MalformBehaviourException;
import com.lcaohoanq.shoppe.exceptions.PasswordWrongFormatException;
import com.lcaohoanq.shoppe.exceptions.PermissionDeniedException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Otp;
import com.lcaohoanq.shoppe.models.Role;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.repositories.RoleRepository;
import com.lcaohoanq.shoppe.repositories.SocialAccountRepository;
import com.lcaohoanq.shoppe.repositories.UserRepository;
import com.lcaohoanq.shoppe.services.mail.IMailService;
import com.lcaohoanq.shoppe.services.otp.OtpService;
import com.lcaohoanq.shoppe.services.role.RoleService;
import com.lcaohoanq.shoppe.services.token.TokenService;
import com.lcaohoanq.shoppe.utils.MessageKey;
import com.lcaohoanq.shoppe.utils.OtpUtils;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final RoleRepository roleRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final LocalizationUtils localizationUtils;
    private final IMailService mailService;
    private final OtpService otpService;
    private final RoleService roleService;
    private final TokenService tokenService;

    @Override
    @Transactional
    public User register(UserRegisterDTO userRegisterDTO) throws Exception {
        if(!userRegisterDTO.password().matches(Regex.PASSWORD_REGEX)){
            throw new PasswordWrongFormatException("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        if (!userRegisterDTO.password().equals(userRegisterDTO.confirmPassword())) {
            throw new MalformBehaviourException("Password and confirm password must be the same");
        }

        String email = userRegisterDTO.email();
        if (userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        User newUser = User.builder()
            .name(userRegisterDTO.name())
            .email(userRegisterDTO.email())
            .password(userRegisterDTO.password())
            .isActive(true)
            .address(userRegisterDTO.address())
            .dateOfBirth(userRegisterDTO.dateOfBirth())
            .avatar(Optional.ofNullable(userRegisterDTO.avatar())
                           .orElse("https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"))
            .build();

        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.password()));

        newUser.setRole(
            roleRepository
                .findById(1L)
                .orElseThrow(() -> new DataNotFoundException("Role not found")));

        User savedUser = userRepository.save(newUser);

        // send OTP email
        String otp = OtpUtils.generateOtp();
        Context context = new Context();
        context.setVariable("name", savedUser.getName());
        context.setVariable("otp", otp);

        mailService.sendMail(
            savedUser.getEmail(),
            "Verify your email",
            EmailCategoriesEnum.OTP.getType(),
            context
        );

        log.info("Send email otp to {}", savedUser.getEmail());

        Otp otpEntity = Otp.builder()
            .email(savedUser.getEmail())
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);

        return savedUser;
    }

    @Override
    public String login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKey.WRONG_PHONE_PASSWORD));
        }
        User existingUser = optionalUser.get();

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(email, password, existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(existingUser);
    }

    //Token
    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String email = jwtTokenUtils.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            );
        }
    }

    @Override
    public void logout(String token, User user) throws Exception {
        if(jwtTokenUtils.isTokenExpired(token)){
            throw new ExpiredTokenException("Token is expired");
        }

        tokenService.deleteToken(token, user);
    }


}
