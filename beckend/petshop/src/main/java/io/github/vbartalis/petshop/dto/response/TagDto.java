package io.github.vbartalis.petshop.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {

    private Long id;
    private String name;
    private String description;
}
