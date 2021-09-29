package io.github.vbartalis.petshop.dto.profile;

import io.github.vbartalis.petshop.dto.profileimage.ProfileImageEmptyDto;
import io.github.vbartalis.petshop.dto.user.UserEmptyDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class ProfileDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String description;
    private Date joinDate;

    private ProfileImageEmptyDto profileImage;
    private UserEmptyDto user;
}
