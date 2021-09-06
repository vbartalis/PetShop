package io.github.vbartalis.petshop.dto.profile;

import io.github.vbartalis.petshop.dto.profileimage.EmptyProfileImageDto;
import io.github.vbartalis.petshop.dto.user.EmptyUserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileDto {


    private Long id;
    private String name;
    private String email;
    private String address;
    private String description;
    private Date joinDate;

    private EmptyProfileImageDto profileImage;
    private EmptyUserDto user;
}
