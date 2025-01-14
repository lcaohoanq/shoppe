package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.role.Role;
import com.lcaohoanq.shoppe.domain.user.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role.id = 2")
    Page<User> findAllStaff(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role.id = 1")
    Page<User> findAllMember(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role.id = 3")
    Page<User> findAllBreeder(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.role.id = 2 AND u.isActive = true")
    Optional<User> findStaffById(Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.role.id = 3 AND u.isActive = true")
    Optional<User> findBreederById(Long id);

    @Query("SELECT u FROM User u WHERE u.role.id = 2 AND u.isActive = true")
    Page<User> findAllStaffWithActive(Pageable pageable);

    @Modifying
    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :id")
    void softDeleteUser(Long id);

    @Modifying
    @Query("UPDATE User u SET u.isActive = true WHERE u.id = :id")
    void restoreUser(Long id);

    @Modifying
    @Query("UPDATE User u SET u.role.id = :roleId WHERE u.id = :id")
    void updateRole(Long id, Long roleId);
}
