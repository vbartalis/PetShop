package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.role.RoleDto;
import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.impl.RoleServiceImpl;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    DtoEntityConverter converter;

    @Operation(
            summary = "Get All Roles.",
            description = "Can be used by Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @GetMapping()
    public List<RoleDto> getRole() {
        List<Role> roles = roleService.getAllRoles();
        return converter.convertToListDto(roles, RoleDto.class);
    }

}
