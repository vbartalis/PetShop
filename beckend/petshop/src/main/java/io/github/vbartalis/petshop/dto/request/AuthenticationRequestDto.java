package io.github.vbartalis.petshop.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
