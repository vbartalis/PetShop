package io.github.vbartalis.petshop.dto.post;

import io.github.vbartalis.petshop.dto.postimage.PostImageEmptyDto;
import io.github.vbartalis.petshop.dto.tag.TagDto;
import io.github.vbartalis.petshop.dto.user.UserEmptyDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private Date creationDate;
    private Date updateDate;
    private Boolean isPublic;
    private UserEmptyDto user;
    private PostImageEmptyDto postImage;
    private List<TagDto> tags;
}
