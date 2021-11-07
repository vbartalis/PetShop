package io.github.vbartalis.petshop.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class ProfileUpdateDto {
    @Size(max = 50, message = "profile name size validation criteria not met")
    private String name;
    @Size(max = 50, message = "profile email size validation criteria not met")
    private String email;
    @Size(max = 255, message = "profile address size validation criteria not met")
    private String address;
    @Size(max = 255, message = "profile description size validation criteria not met")
    private String description;
}

