package io.github.vbartalis.petshop.dto.request;

import io.github.vbartalis.petshop.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostSearchCriteria {
    private Long id;
    private String title;
    private String description;
    private Boolean isPublic;
    private Long userId;
}
