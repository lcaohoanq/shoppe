package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.role.Role;
import com.lcaohoanq.jvservice.domain.role.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "name", source = "userRole")
    RoleResponse toRoleResponse(Role role);
}

