package com.lcaohoanq.jvservice.domain.role;

import java.util.List;

public interface IRoleService {
    List<RoleResponse> getAllRoles();
    RoleResponse createRole(RoleDTO roleDTO);
    void updateRole(long id, RoleDTO roleDTO);
    void deleteRole(Long id);
    RoleResponse getRoleById(Long id);
}
