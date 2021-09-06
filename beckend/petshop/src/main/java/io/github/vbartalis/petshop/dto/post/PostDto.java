package io.github.vbartalis.petshop.dto.post;

import io.github.vbartalis.petshop.dto.postimage.EmptyPostImageDto;
import io.github.vbartalis.petshop.dto.tag.TagDto;
import io.github.vbartalis.petshop.dto.user.EmptyUserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private Date creationDate;
    private Date updateDate;
    private Boolean isPublic;
    private EmptyUserDto user;
    private EmptyPostImageDto postImage;
    private List<TagDto> tags;
}
