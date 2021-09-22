package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.request.UserPage;
import io.github.vbartalis.petshop.dto.request.UserSearchCriteria;
import io.github.vbartalis.petshop.dto.response.UserDto;
import io.github.vbartalis.petshop.dto.user.PatchUserDto;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.impl.UserServiceImpl;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    DtoEntityConverter converter;

    @Autowired
    AuthenticationContext authenticationContext;

    @Operation(summary = "Get all Users. ",
            description = "Can be used by Admin." +
                    "Can be filtered by id, username, isLocked properties. " +
                    "Can be sorted by id, username, expiration properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(UserPage userPage, UserSearchCriteria userSearchCriteria) {
        Page<User> responsePost = userService.getAllUsers(userPage, userSearchCriteria);
        Page<UserDto> response = converter.convertToPageDto(responsePost, UserDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new User.",
            description = "Can be used by Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto dto) {
        User user = converter.convertToEntity(dto, User.class);
        User responseUser = userService.createUser(user);
        return converter.convertToDto(responseUser, UserDto.class);
    }

    @Operation(
            summary = "Update User.",
            description = "Can be used by Admin to update password, isLocked, Expiration, roles properties. " +
                    "Can be used by Owner to update the password property.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public UserDto partialUpdateUser(
            @PathVariable("id") @NotNull Long id,
            @Valid @RequestBody PatchUserDto dto
    ) {
        User user = new User();
        if (authenticationContext.isAdmin()) {
            user = converter.convertToEntity(dto, User.class);
        } else {
            user.setId(id);
            user.setPassword(dto.getPassword());
        }
        User responseUser = userService.partialUpdateUser(id, user);
        return converter.convertToDto(responseUser, UserDto.class);
    }

    @Operation(summary = "Get User by it's Id.",
            description = "Can be used by Owner or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @NotNull Long id) {
        User user = userService.getUserById(id);
        return converter.convertToDto(user, UserDto.class);
    }

}
