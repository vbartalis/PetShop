package io.github.vbartalis.petshop.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImageDto {

    private Long id;
    private byte[] data;
}
