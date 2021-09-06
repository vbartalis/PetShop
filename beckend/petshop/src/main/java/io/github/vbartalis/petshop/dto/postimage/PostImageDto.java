package io.github.vbartalis.petshop.dto.postimage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImageDto {

    private Long id;
    private byte[] data;
}
