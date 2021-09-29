package io.github.vbartalis.petshop.controllers;


import io.github.vbartalis.petshop.dto.authentication.AuthenticationRequestDto;
import io.github.vbartalis.petshop.dto.authentication.AuthenticationResponseDto;
import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.service.impl.AuthenticationService;
import io.github.vbartalis.petshop.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class serves as RestController.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserServiceImpl userService;

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a POST request.
     * It authenticates the provided credentials.
     *
     * @param dto The Data Transfer Object that contains the credentials.
     * @return Returns the username, the jwt token and the roles of the authenticated user inside a {@code PostDto}.
     */
    @Operation(summary = "Sign In method, returns a username, JWT token and roles")
    @PostMapping("/signin")
    public AuthenticationResponseDto createAuthenticationToken(@Valid @RequestBody AuthenticationRequestDto dto) {
        String token = authenticationService.authenticate(dto.getUsername(), dto.getPassword());
        List<String> roles = this.userService.getUserByUsername(dto.getUsername()).getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return new AuthenticationResponseDto(dto.getUsername(), token, roles);
    }
}
