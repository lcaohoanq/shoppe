package com.lcaohoanq.shoppe.services.role;

import com.lcaohoanq.shoppe.dtos.RoleDTO;
import com.lcaohoanq.shoppe.dtos.responses.RoleResponse;
import java.util.List;

public interface IRoleService {
    List<RoleResponse> getAllRoles();
    RoleResponse createRole(RoleDTO roleDTO);
    RoleResponse updateRole(long id, RoleDTO roleDTO);
    void deleteRole(Long id);
    RoleResponse getRoleById(Long id);
}
