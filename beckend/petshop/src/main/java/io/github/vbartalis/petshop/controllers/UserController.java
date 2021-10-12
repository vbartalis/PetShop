package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.user.*;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.service.impl.UserServiceImpl;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * This class serves as RestController.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    DtoEntityConverter converter;
    @Autowired
    AuthenticationContext authenticationContext;

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns a page of {@code UserDto} that is filtered by the provided criteria.
     *
     * @param userPage           The properties of the page returned.
     * @param userSearchCriteria The criteria by which the returned page of entities should be filtered.
     * @return Returns a page of {@code UserDto}.
     */
    @Operation(
            summary = "Get all Users.",
            description = "Can be used by Admin. " +
                    "Can be filtered by id, username, isLocked properties. " +
                    "Can be sorted by id, username, expiration properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(UserPageCriteria userPage, UserSearchCriteria userSearchCriteria) {
        Page<User> responsePost = userService.getAllUsers(userPage, userSearchCriteria);
        Page<UserDto> response = converter.convertToPageDto(responsePost, UserDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a POST request.
     * It creates a new {@code User} Entity.
     *
     * @param dto The Data Transfer Object that contains the properties of the new {@code User} Entity.
     * @return Returns the created {@code User} Entity in the form of {@code UserDto}.
     */
    @Operation(
            summary = "Create a new User.",
            description = "Can be used by Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserCreateDto dto) {
        User user = converter.convertToEntity(dto, User.class);
        User responseUser = userService.createUser(user);
        return converter.convertToDto(responseUser, UserDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PUT request.
     * It updates the specified properties of the specified {@code User} entity.
     *
     * @param id  The id of the {@code User} entity.
     * @param dto The Data Transfer Object that contains the properties of the {@code User}
     *            Entity that needs to be updated.
     * @return Returns the updated {@code User} Entity in the form of {@code UserDto}.
     */
    @Operation(
            summary = "Update a User.",
            description = "Can be used by Admin to update password, isLocked, Expiration, roles properties. " +
                    "Can be used by Owner to update the password property.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#id) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public UserDto partialUpdateUser(
            @PathVariable("id") @NotNull Long id,
            @Valid @RequestBody UserUpdateDto dto
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

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns the specified {@code User} entity in the form of {@code UserDto}.
     *
     * @param id The id of the {@code User} entity.
     * @return Returns the specified {@code User} Entity in the form of {@code UserDto}.
     */
    @Operation(
            summary = "Get User by it's Id.",
            description = "Can be used by Owner or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#id) || hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @NotNull Long id) {
        User user = userService.getUserById(id);
        return converter.convertToDto(user, UserDto.class);
    }

}
