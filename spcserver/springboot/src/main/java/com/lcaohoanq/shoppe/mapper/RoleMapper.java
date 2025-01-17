package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.role.Role;
import com.lcaohoanq.shoppe.domain.role.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "name", source = "userRole")
    RoleResponse toRoleResponse(Role role);
}

