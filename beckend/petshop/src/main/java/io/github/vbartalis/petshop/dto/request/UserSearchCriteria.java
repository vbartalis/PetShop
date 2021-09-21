package io.github.vbartalis.petshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UserSearchCriteria {
    private Long id;
    private String username;
    private Boolean isLocked;
}
