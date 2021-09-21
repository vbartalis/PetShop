package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Role;
import io.github.vbartalis.petshop.repository.entityRepository.UserRepository;
import io.github.vbartalis.petshop.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtTokenProvider.createToken(
                username,
                this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"))
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(toList())
        );
        return token;

    }
}
