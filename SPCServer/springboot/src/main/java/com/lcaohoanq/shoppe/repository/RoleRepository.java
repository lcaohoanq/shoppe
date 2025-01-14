package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.role.Role;
import com.lcaohoanq.shoppe.enums.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByUserRole(UserRole userRole);

}
