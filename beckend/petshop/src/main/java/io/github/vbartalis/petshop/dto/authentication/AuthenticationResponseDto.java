package io.github.vbartalis.petshop.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String username;
    private String jwt;
    private List<String> roles;
}
