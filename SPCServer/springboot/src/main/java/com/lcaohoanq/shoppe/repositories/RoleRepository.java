package com.lcaohoanq.shoppe.repositories;

import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByUserRole(UserRole userRole);

}
