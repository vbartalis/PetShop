package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.repository.entityRepository.UserRepository;
import io.github.vbartalis.petshop.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

/**
 * This is a service class, used to authenticate the user. Its methods are used to service the {@code UserController}
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * This method authenticates a user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return Returns a jwt token for the user if the user is authenticated.
     */
    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtTokenProvider.createToken(
                username,
                this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new NoSuchElementException("Username " + username + " not found"))
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(toList())
        );

    }
}
