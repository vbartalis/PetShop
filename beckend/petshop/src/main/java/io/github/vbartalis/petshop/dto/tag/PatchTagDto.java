package io.github.vbartalis.petshop.dto.tag;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PatchTagDto {

    @NotNull
    private Long id;
    private String name;
    private String description;
}
