package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PatchUserDto {

    @NotNull
    private Long id;
//    @NotEmpty
//    private String username;
//    private String password;
    private Boolean isLocked;
    private Date expiration;
//    private Profile profile;
//    private List<PostDto> posts;
    private List<RoleDto> roles;

}
