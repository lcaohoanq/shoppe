package com.lcaohoanq.jvservice.repository;

import com.lcaohoanq.jvservice.domain.role.Role;
import com.lcaohoanq.common.enums.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByUserRole(UserRole userRole);

}
