package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.profile.ProfileEmptyDto;
import io.github.vbartalis.petshop.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private Boolean isLocked;
    private Date expiration;
    private ProfileEmptyDto profile;
    private List<RoleDto> roles;

}