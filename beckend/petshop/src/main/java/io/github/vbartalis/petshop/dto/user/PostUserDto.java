package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.response.RoleDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostUserDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    private Boolean isLocked;
    @NotNull
    private Date expiration;
//    private Profile profile;
//    private List<PostDto> posts;
    @NotNull
    private List<RoleDto> roles;

}
