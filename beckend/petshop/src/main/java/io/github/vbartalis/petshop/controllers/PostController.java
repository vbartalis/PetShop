package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.response.PostDto;
import io.github.vbartalis.petshop.dto.request.PostPage;
import io.github.vbartalis.petshop.dto.request.PostSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.security.filters.OwnerChecker;
import io.github.vbartalis.petshop.security.methodlevel.IsUser;
import io.github.vbartalis.petshop.service.impl.PostServiceImpl;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @Autowired
    DtoEntityConverter converter;

    @Autowired
    AuthenticationContext authenticationContext;

    @Autowired
    OwnerChecker ownerChecker;

    @Operation(summary = "get all Users, can be ordered by id, title, description, isPublic, userId properties, " +
            "it can also be filtered by id, title, updateDate, creationDate properties",
            description = "Can be used by Admin",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(PostPage postPage, PostSearchCriteria postSearchCriteria) {
        boolean isOwner = false;
        if (postSearchCriteria.getUserId() != null) {
            isOwner = ownerChecker.checkUser(postSearchCriteria.getUserId(), authenticationContext.getAuthentication());
        }
        if (!isOwner && !authenticationContext.isAdmin()) {
            postSearchCriteria.setIsPublic(true);
        }

        Page<Post> responsePost = postService.getAllPosts(postPage, postSearchCriteria);
        Page<PostDto> response = converter.convertToPageDto(responsePost, PostDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "create a new Post", description = "Can be used by User", security = @SecurityRequirement(name = "bearerAuth"))
    @IsUser
    @PostMapping
    public PostDto createPost(@Valid @RequestBody PostDto dto) {
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.createPost(post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    @Operation(summary = "update a Post", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPost(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public PostDto updatePost(
            @PathVariable(value = "id") @NotNull Long id,
            @Valid @RequestBody PostDto dto) {
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.updatePost(id, post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    @Operation(summary = "delete a Post", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPost(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") @NotNull Long id) {
        postService.deletePost(id);
    }

    @Operation(summary = "get Post by it's Id", description = "Can be used by Public, Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") @NotNull Long id) {
        Post responsePost = postService.getPostById(id);
        return converter.convertToDto(responsePost, PostDto.class);
    }
}
