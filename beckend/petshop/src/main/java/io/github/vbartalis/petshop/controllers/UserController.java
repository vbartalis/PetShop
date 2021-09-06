package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.user.PatchUserDto;
import io.github.vbartalis.petshop.dto.user.PatchUserPasswordDto;
import io.github.vbartalis.petshop.dto.user.PostUserDto;
import io.github.vbartalis.petshop.dto.user.UserDto;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.UserService;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "get User By User Id", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @NotNull Long id) {
        User user = userService.findById(id);
        return converter.convertToDto(user, UserDto.class);
    }

    @Operation(summary = "get User By username", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#username, authentication) || hasAuthority('ROLE_ADMIN')")
    @GetMapping("/username/{username}")
    public UserDto getUserByUsername(@PathVariable("username") @NotNull String username) {
        User user = userService.findByUsername(username);
        return converter.convertToDto(user, UserDto.class);
    }

    @Operation(summary = "get Users", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @GetMapping
    public Page<UserDto> getUsers(@Min(value = 1) @RequestParam(value = "page") int page) {
        int pageSize = 10;
        Page<User> users = userService.findAll(page, pageSize);
        return converter.convertToPageDto(users, UserDto.class);
    }

    @Operation(summary = "create a new User", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PostMapping
    public UserDto postUser(@Valid @RequestBody PostUserDto dto) {
        User user = converter.convertToEntity(dto, User.class);
        User responseUser = userService.save(user);
        return converter.convertToDto(responseUser, UserDto.class);
    }

    @Operation(summary = "update User", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PatchMapping
    public UserDto patchUser(@Valid @RequestBody PatchUserDto dto) {
        User user = converter.convertToEntity(dto, User.class);
        User responseUser = userService.patch(user);
        return converter.convertToDto(responseUser, UserDto.class);
    }

    @Operation(summary = "update User Password", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#dto.id, authentication) || hasAuthority('ROLE_ADMIN')")
    @IsAdmin
    @PatchMapping("/password")
    public UserDto patchUserPassword(@Valid @RequestBody PatchUserPasswordDto dto) {
        User user = converter.convertToEntity(dto, User.class);
        User responseUser = userService.patchPassword(user);
        return converter.convertToDto(responseUser, UserDto.class);
    }

}
