package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class UserCreateDto {
    @NotBlank
    @Size(max = 50, message = "user username size validation criteria not met")
    private String username;
    @NotBlank
    @Size(max = 255, message = "user password size validation criteria not met")
    private String password;
    @NotNull
    private Boolean isLocked;
    @NotNull
    private Date expiration;
    private List<RoleDto> roles;

}
