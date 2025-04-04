package com.lcaohoanq.jvservice.domain.user;

import com.lcaohoanq.jvservice.api.PageResponse;
import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.domain.auth.AuthPort.UpdatePasswordDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {

    PageResponse<UserResponse> fetchUser(Pageable pageable);

    String loginOrRegisterGoogle(String email, String name, String googleId, String avatarUrl) throws Exception;

    UserResponse findUserById(long id) throws DataNotFoundException;

    User findUserByEmail(String email) throws DataNotFoundException;

    List<User> getAllUsers();

    User findByUsername(String username) throws DataNotFoundException;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;

    void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;

    @Transactional
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    @Transactional
    User updateUserBalance(Long userId, Long payment) throws Exception;
    
    void bannedUser(Long userId) throws DataNotFoundException;

    void updatePassword(UpdatePasswordDTO updatePasswordDTO) throws Exception;

    void softDeleteUser(Long userId) throws DataNotFoundException;

    void restoreUser(Long userId) throws DataNotFoundException;

    void updateRole(long id, long roleId) throws DataNotFoundException;

    void validateAccountBalance(User user, long basePrice);
    
    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);
    
    Boolean existsById(Long id);

    User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;
}
