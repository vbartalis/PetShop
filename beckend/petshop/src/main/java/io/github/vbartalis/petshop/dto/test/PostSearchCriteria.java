package io.github.vbartalis.petshop.dto.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PostSearchCriteria {
    private Long id;
    private String title;
    private String description;

}
