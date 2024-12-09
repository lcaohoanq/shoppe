package com.lcaohoanq.shoppe.domain.user;

import com.lcaohoanq.shoppe.domain.auth.UpdatePasswordDTO;
import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {

    PageResponse<UserResponse> fetchUser(Pageable pageable);

    String loginOrRegisterGoogle(String email, String name, String googleId, String avatarUrl) throws Exception;

    User findUserById(long id) throws DataNotFoundException;

    User findUserByEmail(String email) throws DataNotFoundException;

    List<User> getAllUsers();

    User findByUsername(String username) throws DataNotFoundException;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;

    void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;

    @Transactional
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    @Transactional
    User updateUserBalance(Long userId, Long payment) throws Exception;

    void updateAccountBalance(Long userId, Long payment) throws Exception;

    void bannedUser(Long userId) throws DataNotFoundException;

    void updatePassword(UpdatePasswordDTO updatePasswordDTO) throws Exception;

    void softDeleteUser(Long userId) throws DataNotFoundException;

    void restoreUser(Long userId) throws DataNotFoundException;

    void updateRole(long id, long roleId) throws DataNotFoundException;

    void validateAccountBalance(User user, long basePrice);
}
