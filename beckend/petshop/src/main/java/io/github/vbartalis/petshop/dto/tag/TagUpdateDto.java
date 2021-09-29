package io.github.vbartalis.petshop.dto.tag;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class TagUpdateDto {
    @Size(max = 20, message = "tag name size validation criteria not met")
    private String name;
    @Size(max = 255, message = "tag description size validation criteria not met")
    private String description;
}
