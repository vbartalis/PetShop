package io.github.vbartalis.petshop.dto.post;

import io.github.vbartalis.petshop.dto.tag.EmptyTagDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PostPutDto {

    @Size(max = 40)
    private String title;
    @Size(max = 255, message = "post description size validation criteria not met")
    private String description;
    private Boolean isPublic;
    private List<EmptyTagDto> tags;
}
