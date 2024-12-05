package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.dtos.request.RoleDTO;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.dtos.responses.RoleResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.services.role.IRoleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.<List<RoleResponse>>builder()
                .message("Roles fetched successfully")
                .data(roleService.getAllRoles())
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.<RoleResponse>builder()
                .message("Role fetched successfully")
                .data(roleService.getRoleById(id))
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<RoleResponse>> create(
        @Valid @RequestBody RoleDTO roleDTO,
        BindingResult bindingResult) {

        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<RoleResponse>builder()
                .message("Role created successfully")
                .data(roleService.createRole(roleDTO))
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .build());

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<RoleResponse>> update(
        @PathVariable Long id,
        @Valid @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok(
            ApiResponse.<RoleResponse>builder()
                .message("Role updated successfully")
                .data(roleService.getRoleById(id))
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<RoleResponse>> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(
            ApiResponse.<RoleResponse>builder()
                .message("Role deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(null)
                .build()
        );
    }

}
