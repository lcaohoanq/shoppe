package com.lcaohoanq.shoppe.services.role;

import com.lcaohoanq.shoppe.dtos.RoleDTO;
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
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
            .stream()
            .map(DTOConverter::toRoleResponse)
            .toList();
    }

    @Override
    public RoleResponse createRole(RoleDTO roleDTO) {
        if(roleRepository.findByUserRole(roleDTO.userRole()).isPresent()){
            throw new DataAlreadyExistException("Role with name " + roleDTO.userRole().name() + " already exist");
        }

        Role newRole = Role.builder()
            .userRole(roleDTO.userRole())
            .build();

        return DTOConverter.toRoleResponse(roleRepository.save(newRole));
    }

    @Override
    public RoleResponse updateRole(long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
        existingRole.setUserRole(roleDTO.userRole());
        return DTOConverter.toRoleResponse(roleRepository.save(existingRole));
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
            .map(DTOConverter::toRoleResponse)
            .orElseThrow(() -> new DataNotFoundException("Role with id " + id + " not found"));
    }
}
