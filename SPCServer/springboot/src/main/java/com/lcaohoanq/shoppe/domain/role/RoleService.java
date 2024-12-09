package com.lcaohoanq.shoppe.domain.role;

import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.exception.MalformDataException;
import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.util.DTOConverter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService, DTOConverter {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
            .stream()
            .map(this::toRoleResponse)
            .toList();
    }

    @Override
    public RoleResponse createRole(RoleDTO roleDTO) {
        if(roleRepository.findByUserRole(roleDTO.userRole()).isPresent()){
            throw new DataAlreadyExistException("Role with name " + roleDTO.userRole().name() + " already exist");
        }

        if (Arrays.stream(UserRole.values())
            .noneMatch(role -> role.name().equals(roleDTO.userRole().name()))) {
            throw new MalformDataException("Role with name " + roleDTO.userRole().name() + " is not valid");
        }

        Role newRole = Role.builder()
            .userRole(roleDTO.userRole())
            .build();

        return toRoleResponse(roleRepository.save(newRole));
    }

    @Override
    public void updateRole(long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
        existingRole.setUserRole(roleDTO.userRole());
        roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
        roleRepository.delete(existingRole);
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        return roleRepository.findById(id)
            .map(this::toRoleResponse)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
    }
}
