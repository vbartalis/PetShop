package io.github.vbartalis.petshop.dto.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class PostSearchCriteria {
    private Long id;
    @Size(max = 50, message = "PostSearchCriteria title size validation criteria not met")
    private String title;
    @Size(max = 255, message = "PostSearchCriteria description size validation criteria not met")
    private String description;
    private Boolean isPublic;
    private Long userId;
    private Long[] tagIds;
}
