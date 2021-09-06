package io.github.vbartalis.petshop.controllers;


import io.github.vbartalis.petshop.dto.authentication.AuthenticationRequestDto;
import io.github.vbartalis.petshop.dto.authentication.AuthenticationResponseDto;
import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.service.AuthenticationService;
import io.github.vbartalis.petshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;

    @Operation(summary = "Sign In method, returns a username, JWT token and roles")
    @PostMapping("/signin")
    public AuthenticationResponseDto createAuthenticationToken(@Valid @RequestBody AuthenticationRequestDto request){
        log.warn("auth post");
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        List<String> roles = this.userService.findByUsername(request.getUsername()).getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return new AuthenticationResponseDto(request.getUsername(), token, roles);
    }
}
