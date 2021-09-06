package io.github.vbartalis.petshop.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PatchUserPasswordDto {

    @NotNull
    private Long id;
//    @NotEmpty
//    private String username;
    @NotBlank
    private String password;
//    private Boolean isLocked;
//    private Date expiration;
//    private Profile profile;
//    private List<PostDto> posts;
//    private List<RoleDto> roles;

}
