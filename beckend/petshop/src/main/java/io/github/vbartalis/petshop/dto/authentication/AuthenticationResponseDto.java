package io.github.vbartalis.petshop.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@Builder(access = AccessLevel.PUBLIC)
public class AuthenticationResponseDto {
    private String username;
    private String jwt;
    private List<String> roles;
}
