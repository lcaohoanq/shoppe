package com.lcaohoanq.shoppe.domain.user;

import com.lcaohoanq.shoppe.component.JwtTokenUtils;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.domain.auth.UpdatePasswordDTO;
import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.enums.ProviderName;
import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.enums.UserStatus;
import com.lcaohoanq.shoppe.exception.EmailAlreadyUsedException;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.exception.MalformDataException;
import com.lcaohoanq.shoppe.exception.PermissionDeniedException;
import com.lcaohoanq.shoppe.exception.PhoneAlreadyUsedException;
import com.lcaohoanq.shoppe.exception.UpdateEmailException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.role.Role;
import com.lcaohoanq.shoppe.domain.socialaccount.SocialAccount;
import com.lcaohoanq.shoppe.domain.role.RoleRepository;
import com.lcaohoanq.shoppe.domain.socialaccount.SocialAccountRepository;
import com.lcaohoanq.shoppe.domain.mail.IMailService;
import com.lcaohoanq.shoppe.domain.otp.OtpService;
import com.lcaohoanq.shoppe.domain.role.RoleService;
import com.lcaohoanq.shoppe.mapper.UserMapper;
import com.lcaohoanq.shoppe.util.DTOConverter;
import com.lcaohoanq.shoppe.constant.MessageKey;
import com.lcaohoanq.shoppe.util.PaginationConverter;
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService, PaginationConverter, DTOConverter {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RoleRepository roleRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final LocalizationUtils localizationUtils;
    private final IMailService mailService;
    private final OtpService otpService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    
    @Override
    public PageResponse<UserResponse> fetchUser(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return mapPageResponse(
            usersPage,
            pageable,
            userMapper::toUserResponse,
            "Get all users successfully");
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
    public User findUserById(long id) throws DataNotFoundException {
        return userRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)));
    }

    @Override
    public User findUserByEmail(String email) throws DataNotFoundException {
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
        User existingUser = findUserById(userId);
        if (!existingUser.isActive()) {
            throw new MalformDataException("User is already deleted");
        }
        userRepository.softDeleteUser(userId);
    }

    @Override
    @Transactional
    public void restoreUser(Long userId) throws DataNotFoundException {
        User existingUser = findUserById(userId);
        if (existingUser.isActive()) {
            throw new MalformDataException("User is already active");
        }
        userRepository.restoreUser(userId);
    }

    //need send email
    @Override
    @Transactional
    public void updateRole(long id, long roleId) throws DataNotFoundException {
        findUserById(id);
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
//            throw new MalformDataException("You don't have enough money to register Product to SHOPPE");
//        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override 
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
