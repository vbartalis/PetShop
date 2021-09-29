package io.github.vbartalis.petshop.dto.role;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class RoleDto {
    private Long id;
    private String name;
}
