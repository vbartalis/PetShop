package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.user.UserDto;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.security.methodlevel.IsUser;
import io.github.vbartalis.petshop.service.impl.UserServiceImpl;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * This class serves as an ApiController, it is used by all methods that do not fit the REST architectural style.
 */
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    DtoEntityConverter converter;
    @Autowired
    AuthenticationContext authenticationContext;

    /**
     * This method serves as aa API Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns the current users {@code User} entity in the form of {@code UserDto}.
     *
     * @return Returns the specified {@code User} Entity in the form of {@code UserDto}.
     */
    @Operation(
            summary = "Get current User.",
            description = "Can be used by User or Admin.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsUser
    @IsAdmin
    @GetMapping("/currentUser")
    public UserDto getCurrentUser() {
        User user = userService.getUserByUsername(authenticationContext.getUsername());
        return converter.convertToDto(user, UserDto.class);
    }
}
