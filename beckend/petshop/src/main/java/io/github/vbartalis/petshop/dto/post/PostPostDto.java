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

//    private Long id;
    @NotBlank
    @Size(max = 40)
    private String title;
    @NotBlank
    @Size(max = 255)
    private String description;
//    private Date creationDate;
//    private Date updateDate;
    @NotNull
    private Boolean isPublic;
//    private User user;
//    private PostImage postImage;
    private List<EmptyTagDto> tags;
}