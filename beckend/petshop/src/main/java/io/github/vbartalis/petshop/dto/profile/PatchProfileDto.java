package io.github.vbartalis.petshop.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PatchProfileDto {

    @NotNull
    private Long id;
    private String name;
    private String email;
    private String address;
    private String description;
}

