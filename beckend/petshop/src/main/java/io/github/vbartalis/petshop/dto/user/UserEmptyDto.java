package io.github.vbartalis.petshop.dto.user;

import io.github.vbartalis.petshop.dto.profile.ProfileEmptyDto;
import io.github.vbartalis.petshop.entity.Profile;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class UserEmptyDto {
    private Long id;
    private ProfileEmptyDto profile;
}
