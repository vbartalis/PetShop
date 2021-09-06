package io.github.vbartalis.petshop.dto.tag;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostTagDto {

//    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
