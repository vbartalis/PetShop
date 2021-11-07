package io.github.vbartalis.petshop.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class UserSearchCriteria {
    private Long id;
    @Size(max = 50, message = "user username size validation criteria not met")
    private String username;
    private Boolean isLocked;
}
