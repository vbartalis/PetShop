package io.github.vbartalis.petshop.dto.post;

import io.github.vbartalis.petshop.dto.tag.TagEmptyDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class PostUpdateDto {
    @Size(max = 50, message = "post title size validation criteria not met")
    private String title;
    @Size(max = 255, message = "post description size validation criteria not met")
    private String description;
    private Boolean isPublic;
    private List<TagEmptyDto> tags;
}
