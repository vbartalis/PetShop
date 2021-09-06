package io.github.vbartalis.petshop.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchPublicPostDto {
    private List<Long> ids;
}
