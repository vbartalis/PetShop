package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.profile.EmptyProfileDto;
import io.github.vbartalis.petshop.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
//    private String password;
    private Boolean isLocked;
    private Date expiration;
    private EmptyProfileDto profile;
//    private List<PostDto> posts;
    private List<RoleDto> roles;

}