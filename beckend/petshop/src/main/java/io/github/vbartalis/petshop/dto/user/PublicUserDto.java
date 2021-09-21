package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.response.ProfileDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicUserDto {

    private Long id;
    private String username;
//    private String password;
//    private Boolean isLocked;
//    private Date expiration;
    private ProfileDto profile;
//    private List<PostDto> posts;
//    private List<RoleDto> roles;

}
