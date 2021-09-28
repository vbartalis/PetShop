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
public class PostPostDto {

    @NotBlank
    @Size(max = 40)
    private String title;
    @NotBlank
    @Size(max = 255)
    private String description;
    @NotNull
    private Boolean isPublic;
    private List<EmptyTagDto> tags;
}
