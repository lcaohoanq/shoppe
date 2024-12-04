package com.lcaohoanq.shoppe.services.role;

import com.lcaohoanq.shoppe.dtos.request.RoleDTO;
import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Role;
import com.lcaohoanq.shoppe.repositories.RoleRepository;
import com.lcaohoanq.shoppe.dtos.responses.RoleResponse;
import com.lcaohoanq.shoppe.utils.DTOConverter;
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

        for(UserRole userRole : UserRole.values()){
            //if not exist throw exception
            if(!userRole.name().equals(roleDTO.userRole().name())){
                throw new DataNotFoundException("Role with name " + roleDTO.userRole().name() + " is not valid");
            }
        }

        Role newRole = Role.builder()
            .userRole(roleDTO.userRole())
            .build();

        return toRoleResponse(roleRepository.save(newRole));
    }

    @Override
    public RoleResponse updateRole(long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
        existingRole.setUserRole(roleDTO.userRole());
        return toRoleResponse(roleRepository.save(existingRole));
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
