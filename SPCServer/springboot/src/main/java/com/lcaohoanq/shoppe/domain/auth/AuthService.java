package com.lcaohoanq.shoppe.domain.auth;

import com.lcaohoanq.shoppe.component.JwtTokenUtils;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.constant.Regex;
import com.lcaohoanq.shoppe.domain.cart.Cart;
import com.lcaohoanq.shoppe.domain.cart.CartRepository;
import com.lcaohoanq.shoppe.domain.cart.CartService;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.enums.Country;
import com.lcaohoanq.shoppe.enums.Currency;
import com.lcaohoanq.shoppe.enums.UserStatus;
import com.lcaohoanq.shoppe.exception.ExpiredTokenException;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.exception.PasswordWrongFormatException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.otp.Otp;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.wallet.Wallet;
import com.lcaohoanq.shoppe.domain.role.RoleRepository;
import com.lcaohoanq.shoppe.domain.socialaccount.SocialAccountRepository;
import com.lcaohoanq.shoppe.domain.user.UserRepository;
import com.lcaohoanq.shoppe.domain.wallet.WalletRepository;
import com.lcaohoanq.shoppe.domain.mail.IMailService;
import com.lcaohoanq.shoppe.domain.otp.OtpService;
import com.lcaohoanq.shoppe.domain.role.RoleService;
import com.lcaohoanq.shoppe.domain.token.TokenService;
import com.lcaohoanq.shoppe.domain.user.UserService;
import com.lcaohoanq.shoppe.mapper.UserMapper;
import com.lcaohoanq.shoppe.constant.MessageKey;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    private final RoleService roleService;
    private final TokenService tokenService;
    private final OtpService otpService;
    private final UserService userService;
    private final WalletRepository walletRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public User register(AccountRegisterDTO accountRegisterDTO) throws Exception {

        if (!accountRegisterDTO.password().matches(Regex.PASSWORD_REGEX)) {
            throw new PasswordWrongFormatException(
                "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        if (!accountRegisterDTO.password().equals(accountRegisterDTO.confirmPassword())) {
            throw new MalformBehaviourException("Password and confirm password must be the same");
        }

        String email = accountRegisterDTO.email();
        if (userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(accountRegisterDTO.phoneNumber())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        HttpServletRequest request =
            ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
        String acceptLanguage = request.getHeader("Accept-Language");
        String preferredLanguage = String.valueOf(
            Optional.ofNullable(accountRegisterDTO.preferredLanguage())
                .orElse(acceptLanguage == null || acceptLanguage.isEmpty()
                            ? Country.UNITED_STATES
                            : Country.valueOf(acceptLanguage.toUpperCase())));

        String preferredCurrency = String.valueOf(
            Optional.ofNullable(accountRegisterDTO.preferredCurrency())
                .orElse(Currency.USD));

        return Single.fromCallable(() -> {
                User newUser = User.builder()
                    .name(accountRegisterDTO.name())
                    .email(accountRegisterDTO.email())
                    .password(passwordEncoder.encode(accountRegisterDTO.password()))
                    .phoneNumber(accountRegisterDTO.phoneNumber())
                    .isActive(true)
                    .gender(accountRegisterDTO.gender())
                    .status(UserStatus.UNVERIFIED)
                    .address(accountRegisterDTO.address())
                    .dateOfBirth(accountRegisterDTO.dateOfBirth())
                    .preferredLanguage(preferredLanguage)
                    .preferredCurrency(preferredCurrency)
                    .avatar(Optional.ofNullable(accountRegisterDTO.avatar())
                                .orElse(
                                    "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg"))
                    .role(roleRepository
                              .findById(1L)
                              .orElseThrow(() -> new DataNotFoundException("Role not found")))
                    .build();

                // Step 1: Save the user first without the wallet
                newUser = userRepository.save(newUser);

                // Step 2: Create and save the wallet with a reference to the saved user
                Wallet newWallet = Wallet.builder()
                    .balance(0F)
                    .user(newUser)  // Set the saved user
                    .build();

                newWallet = walletRepository.save(newWallet);

                Cart newCart = cartRepository.findById
                    (cartService.create(newUser.getId()).id())
                    .orElseThrow(() -> new DataNotFoundException("Cart not found"));

                // Step 3: Set the wallet on the user and save the user again
                newUser.setWallet(newWallet);
                newUser.setCart(newCart);
                userRepository.save(newUser);

                return newUser;
            })
            .flatMap(mailService::createEmailVerification)  // Chain the email sending
            .subscribeOn(Schedulers.io())
            .blockingGet();
    }

    @Override
    public String login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.WRONG_PHONE_PASSWORD));
        }
        User existingUser = optionalUser.get();

        existingUser.setLastLoginTimestamp(LocalDateTime.now());
        userRepository.save(existingUser);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(email, password, existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(existingUser);
    }

    //Token
    @Override
    public UserResponse getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String email = jwtTokenUtils.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return userMapper.toUserResponse(user.get());
        } else {
            throw new Exception(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            );
        }
    }

    @Override
    public void logout(String token, User user) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }

        tokenService.deleteToken(token, user);
    }

    @Transactional
    @Override
    public void verifyOtpToVerifyUser(Long userId, String otp) throws Exception {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));

        if (user.getStatus() == UserStatus.VERIFIED) {
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_ALREADY_VERIFIED)
            );
        }

        if (user.getStatus() == UserStatus.BANNED) {
            throw new DataNotFoundException("User is banned");
        }

        Otp otpEntity = getOtpByEmailOtp(user.getEmail(), otp);

        //check the otp is expired or not
        if (otpEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
            otpEntity.setExpired(true);
            otpService.disableOtp(otpEntity.getId());
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.OTP_EXPIRED)
            );
        }

        if (!otpEntity.getOtp().equals(String.valueOf(otp))) {
            throw new DataNotFoundException("Invalid OTP");
        }

        otpEntity.setUsed(true);
        otpService.disableOtp(otpEntity.getId());
        user.setStatus(UserStatus.VERIFIED);
        userRepository.save(user);
    }

    private Otp getOtpByEmailOtp(String email, String otp) {
        return otpService.getOtpByEmailOtp(email, otp)
            .orElseThrow(
                () -> new DataNotFoundException("OTP is not correct, please try again later"));
    }

    @Transactional
    @Override
    public void verifyOtpIsCorrect(Long userId, String otp) throws Exception {
        UserResponse user = userService.findUserById(userId);

        Otp otpEntity = getOtpByEmailOtp(user.email(), otp);

        //check the otp is expired or not
        if (otpEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
            otpEntity.setExpired(true);
            otpService.disableOtp(otpEntity.getId());
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.OTP_EXPIRED)
            );
        }

        if (!otpEntity.getOtp().equals(String.valueOf(otp))) {
            throw new DataNotFoundException("Invalid OTP");
        }

        otpEntity.setUsed(true);
        otpService.disableOtp(otpEntity.getId());
    }

}
