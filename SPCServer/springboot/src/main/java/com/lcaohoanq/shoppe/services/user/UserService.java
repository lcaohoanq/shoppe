package com.lcaohoanq.shoppe.services.user;

import com.lcaohoanq.shoppe.components.JwtTokenUtils;
import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.constants.BusinessNumber;
import com.lcaohoanq.shoppe.dtos.UpdatePasswordDTO;
import com.lcaohoanq.shoppe.dtos.UpdateUserDTO;
import com.lcaohoanq.shoppe.dtos.responses.UserResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.enums.ProviderName;
import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.enums.UserStatus;
import com.lcaohoanq.shoppe.exceptions.BiddingRuleException;
import com.lcaohoanq.shoppe.exceptions.EmailAlreadyUsedException;
import com.lcaohoanq.shoppe.exceptions.MalformBehaviourException;
import com.lcaohoanq.shoppe.exceptions.MalformDataException;
import com.lcaohoanq.shoppe.exceptions.PermissionDeniedException;
import com.lcaohoanq.shoppe.exceptions.PhoneAlreadyUsedException;
import com.lcaohoanq.shoppe.exceptions.UpdateEmailException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.metadata.PaginationMeta;
import com.lcaohoanq.shoppe.models.Otp;
import com.lcaohoanq.shoppe.models.Role;
import com.lcaohoanq.shoppe.models.SocialAccount;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.repositories.RoleRepository;
import com.lcaohoanq.shoppe.repositories.SocialAccountRepository;
import com.lcaohoanq.shoppe.repositories.UserRepository;
import com.lcaohoanq.shoppe.services.mail.IMailService;
import com.lcaohoanq.shoppe.services.otp.OtpService;
import com.lcaohoanq.shoppe.services.role.RoleService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.MessageKey;
import com.lcaohoanq.shoppe.utils.PaginationConverter;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

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
    private final PaginationConverter<User> paginationConverter;

    @Override
    public PageResponse<UserResponse> fetchUser(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserResponse> userResponses = usersPage.getContent().stream()
            .map(DTOConverter::toUserResponse)
            .toList();

        return PageResponse.<UserResponse>pageBuilder()
            .message("Users fetched successfully")
            .statusCode(HttpStatus.OK.value())
            .isSuccess(true)
            .pagination(paginationConverter.toPaginationMeta(usersPage, pageable))
            .data(userResponses)
            .build();
    }

    @Override
    public String loginOrRegisterGoogle(String email, String name, String googleId,
                                        String avatarUrl) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = null;
        SocialAccount socialAccount;

        if (optionalUser.isEmpty()) {
            // Register new user
            Role memberRole = roleRepository.findByUserRole(UserRole.MEMBER)
                .orElseThrow(() -> new DataNotFoundException("Default MEMBER role not found"));

            User newUser = User.builder()
                .name(name)
                .email(email)
                .avatar(avatarUrl)
                .role(memberRole)
                .build();

            SocialAccount newSocialAccount = SocialAccount.builder()
                .providerName(ProviderName.GOOGLE)
                .name(name)
                .email(email)
                .build();

            user = userRepository.save(newUser);
            socialAccountRepository.save(newSocialAccount);
        }

        return jwtTokenUtils.generateToken(user);
    }

    @Override
    public User getUserById(long id) throws DataNotFoundException {
        return userRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)));
    }

    @Override
    public User getUserByEmail(String email) throws DataNotFoundException {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new DataNotFoundException("User not found: " + email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) throws DataNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));
    }

    @Transactional
    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));

        // Check if the email is being changed and if it already exists for another user
        String newEmail = updatedUserDTO.email();

        if (newEmail != null && !newEmail.isEmpty()) {
            // Check if the new email is different from the current user's email
            if (!newEmail.equals(existingUser.getEmail())) {
                // Check if the new email is already in use by another user
                Optional<User> userWithNewEmail = userRepository.findByEmail(newEmail);

                if (userWithNewEmail.isPresent()) {
                    throw new EmailAlreadyUsedException("This email address is already registered");
                }

                // If not, update the current user's email
                existingUser.setEmail(newEmail);
            }
            // If the email is the same as the current one, no changes are needed
        } else {
            throw new UpdateEmailException("This email cannot be empty");
        }

        // Check if the phoneNumber number is being changed and if it already exists for another user
        String newPhoneNumber = updatedUserDTO.phoneNumber();

        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            // Check if the new phoneNumber number is different from the current user's phoneNumber number
            if (!newPhoneNumber.equals(existingUser.getPhoneNumber())) {
                // Check if the new phoneNumber number is already in use by another user
                Optional<User> userWithNewPhoneNumber = userRepository.findByPhoneNumber(
                    newPhoneNumber);

                if (userWithNewPhoneNumber.isPresent()) {
                    throw new PhoneAlreadyUsedException(
                        "This phoneNumber number is already registered");
                }

                // If not, update the current user's phoneNumber number
                existingUser.setPhoneNumber(newPhoneNumber);
            }
            // If the phoneNumber number is the same as the current one, no changes are needed
        } else {
            existingUser.setPhoneNumber(null);
        }

        // Update user information based on the DTO
        if (updatedUserDTO.name() != null) {
            existingUser.setName(updatedUserDTO.name());
        }
        if (updatedUserDTO.address() != null) {
            existingUser.setAddress(updatedUserDTO.address());
        }
        if (updatedUserDTO.status() != null) {
            existingUser.setStatus(UserStatus.valueOf(updatedUserDTO.status()));
        }
        if (updatedUserDTO.dob() != null) {
            existingUser.setDateOfBirth(updatedUserDTO.dob());
        }
        if (updatedUserDTO.avatar() != null) {
            existingUser.setAvatar(updatedUserDTO.avatar());
        }

        // Update the password if it is provided in the DTO
        if (updatedUserDTO.password() != null
            && !updatedUserDTO.password().isEmpty()) {
            if (!updatedUserDTO.password().equals(updatedUserDTO.confirmPassword())) {
                throw new DataNotFoundException("Password and confirm password must be the same");
            }
            String newPassword = updatedUserDTO.password();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        //existingUser.setRole(updatedRole);
        // Save the updated user
        return userRepository.save(existingUser);
    }

    @Transactional
    @Override
    public User updateUserBalance(Long userId, Long payment) throws Exception {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException(
//                        localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
//                ));
//
//        if (user.getAccountBalance() < payment) {
//            throw new BiddingRuleException("Not enough money to make payment");
//        }
//
//        user.setAccountBalance(user.getAccountBalance() - payment);
//
//        return userRepository.save(user);
        return null;
    }

    @Transactional
    @Retryable(
        retryFor = {MessagingException.class},  // Retry only for specific exceptions
        maxAttempts = 3,                       // Maximum retry attempts
        backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    @Override
    public void updateAccountBalance(Long userId, Long payment) throws Exception {
//        User existingUser = userRepository.findById(userId)
//            .orElseThrow(() -> new DataNotFoundException(
//                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
//            ));
//        existingUser.setAccountBalance(existingUser.getAccountBalance() + payment);
//
//        Context context = new Context();
//        context.setVariable("name", existingUser.getFirstName() + " " + existingUser.getLastName());
//        context.setVariable("amount", payment);
//        context.setVariable("balance", existingUser.getAccountBalance());
//
//        try {
//            mailService.sendMail(
//                existingUser.getEmail(),
//                "Account balance updated",
//                EmailCategoriesEnum.BALANCE_FLUCTUATION.getType(),
//                context
//            );
//        } catch (MessagingException e) {
//            log.error("Failed to send email to {}", existingUser.getEmail(), e);
//            throw new MessagingException(String.format("Failed to send email to %s", existingUser.getEmail()));
//        }
//
//        log.info("User {} balance updated. New balance: {}", userId, existingUser.getAccountBalance());
//        userRepository.save(existingUser);
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

    @Transactional
    @Override
    public void verifyOtpIsCorrect(Long userId, String otp) throws Exception {
        User user = getUserById(userId);

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
    }

    private Otp getOtpByEmailOtp(String email, String otp) {
        return otpService.getOtpByEmailOtp(email, otp)
            .orElseThrow(
                () -> new DataNotFoundException("OTP is not correct, please try again later"));
    }

    @Override
    public void bannedUser(Long userId) throws DataNotFoundException {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));

        user.setStatus(UserStatus.BANNED);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) throws Exception {
        User existingUser = userRepository.findByEmail(updatePasswordDTO.email())
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));

        if (!existingUser.isActive()) {
            throw new MalformBehaviourException(MessageKey.USER_NOT_FOUND);
        }

        if (existingUser.getStatus() != UserStatus.VERIFIED) {
            throw new MalformBehaviourException("User do not verified their account");
        }

        if (existingUser.getRole().getId() == 4) {
            throw new PermissionDeniedException("Cannot change password for this account");
        }

        existingUser.setPassword(passwordEncoder.encode(updatePasswordDTO.newPassword()));

        mailService.sendMail(
            existingUser.getEmail(),
            "Password updated successfully",
            EmailCategoriesEnum.RESET_PASSWORD.getType(),
            new Context()
        );

        userRepository.save(existingUser);
    }

    //need send email
    @Override
    @Transactional
    public void softDeleteUser(Long userId) throws DataNotFoundException {
        User existingUser = getUserById(userId);
        if (!existingUser.isActive()) {
            throw new MalformDataException("User is already deleted");
        }
        userRepository.softDeleteUser(userId);
    }

    @Override
    @Transactional
    public void restoreUser(Long userId) throws DataNotFoundException {
        User existingUser = getUserById(userId);
        if (existingUser.isActive()) {
            throw new MalformDataException("User is already active");
        }
        userRepository.restoreUser(userId);
    }

    //need send email
    @Override
    @Transactional
    public void updateRole(long id, long roleId) throws DataNotFoundException {
        getUserById(id);
        roleService.getRoleById(roleId);
        userRepository.updateRole(id, roleId);
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));
        existingUser.setActive(active);
        userRepository.save(existingUser);
    }

    @Override
    public void validateAccountBalance(User user, long basePrice) {
//        if (user.getAccountBalance() < Math.floorDiv(basePrice, BusinessNumber.FEE_ADD_KOI_TO_AUCTION)) {
//            throw new MalformDataException("You don't have enough money to register Koi to Auction");
//        }
    }

}
