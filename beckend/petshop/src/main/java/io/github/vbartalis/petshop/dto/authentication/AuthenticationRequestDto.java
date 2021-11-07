package io.github.vbartalis.petshop.dto.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class AuthenticationRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
