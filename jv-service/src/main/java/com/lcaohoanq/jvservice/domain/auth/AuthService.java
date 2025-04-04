package com.lcaohoanq.jvservice.domain.auth;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.component.JwtTokenUtils;
import com.lcaohoanq.jvservice.component.LocalizationUtils;
import com.lcaohoanq.jvservice.constant.EmailSubject;
import com.lcaohoanq.jvservice.constant.MessageKey;
import com.lcaohoanq.jvservice.constant.Regex;
import com.lcaohoanq.jvservice.domain.auth.AuthPort.AccountRegisterDTO;
import com.lcaohoanq.jvservice.domain.auth.AuthPort.LoginResponse;
import com.lcaohoanq.jvservice.domain.cart.Cart;
import com.lcaohoanq.jvservice.repository.CartRepository;
import com.lcaohoanq.jvservice.domain.cart.ICartService;
import com.lcaohoanq.jvservice.domain.mail.IMailService;
import com.lcaohoanq.jvservice.domain.otp.IOtpService;
import com.lcaohoanq.jvservice.domain.otp.Otp;
import com.lcaohoanq.jvservice.domain.role.IRoleService;
import com.lcaohoanq.jvservice.repository.RoleRepository;
import com.lcaohoanq.jvservice.repository.SocialAccountRepository;
import com.lcaohoanq.jvservice.domain.token.ITokenService;
import com.lcaohoanq.jvservice.domain.token.Token;
import com.lcaohoanq.jvservice.domain.token.TokenPort.RefreshTokenDTO;
import com.lcaohoanq.jvservice.domain.user.IUserService;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.repository.UserRepository;
import com.lcaohoanq.jvservice.domain.user.UserResponse;
import com.lcaohoanq.jvservice.domain.wallet.Wallet;
import com.lcaohoanq.jvservice.repository.WalletRepository;
import com.lcaohoanq.common.enums.Country;
import com.lcaohoanq.common.enums.Currency;
import com.lcaohoanq.common.enums.EmailCategoriesEnum;
import com.lcaohoanq.common.enums.UserStatus;
import com.lcaohoanq.jvservice.exception.ExpiredTokenException;
import com.lcaohoanq.jvservice.exception.MalformBehaviourException;
import com.lcaohoanq.jvservice.exception.PasswordWrongFormatException;
import com.lcaohoanq.jvservice.mapper.TokenMapper;
import com.lcaohoanq.jvservice.mapper.UserMapper;
import com.lcaohoanq.common.utils.Identifiable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.Context;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthService implements IAuthService, Identifiable {
    
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtTokenUtils jwtTokenUtils;
    RoleRepository roleRepository;
    SocialAccountRepository socialAccountRepository;
    LocalizationUtils localizationUtils;
    IMailService mailService;
    IRoleService roleService;
    ITokenService tokenService;
    IOtpService otpService;
    IUserService userService;
    WalletRepository walletRepository;
    CartRepository cartRepository;
    ICartService cartService;
    UserMapper userMapper;
    TokenMapper tokenMapper;
    HttpServletRequest request;

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
    public AuthPort.LoginResponse login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException(
                localizationUtils.getLocalizedMessage(MessageKey.WRONG_PHONE_PASSWORD));
        }

        User existingUser = optionalUser.get();

        existingUser.setLastLoginTimestamp(LocalDateTime.now());
        userRepository.save(existingUser);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                email,
                password,
                existingUser.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        String token = jwtTokenUtils.generateToken(existingUser);

        UserResponse userDetail = getUserDetailsFromToken(token);

        Token jwtToken = tokenService.addToken(
            userDetail.id(),
            token,
            isMobileDevice(request.getHeader("User-Agent")));

        log.info("New user logged in successfully");

        return new LoginResponse(
            tokenMapper.toTokenResponse(jwtToken)
//            ,userDetail
        );
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

    @Override
    public AuthPort.LoginResponse refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
        User userDetail = userService.getUserDetailsFromRefreshToken(
            refreshTokenDTO.refreshToken());
        Token jwtToken = tokenService.refreshToken(refreshTokenDTO.refreshToken(), userDetail);
        return new AuthPort.LoginResponse(tokenMapper.toTokenResponse(jwtToken));
    }

    @Override
    @Transactional
    public void sendEmailOtp(User existingUser) throws MessagingException {
        Context context = new Context();
        String otp = otpService.generateOtp();
        context.setVariable("name", existingUser.getName());
        context.setVariable("otp", otp);

        mailService.sendMail(existingUser.getEmail(),
                             EmailSubject.subjectForgotPassword(existingUser.getName()),
                             EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                             context);

        Otp otpEntity = Otp.builder()
            .email(existingUser.getEmail())
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);
    }

}
