package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.post.*;
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
import org.springframework.security.access.AccessDeniedException;
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

    @Operation(
            summary = "Get all Posts.",
            description = "Can be filtered by id, title, description, userId properties. " +
                    "Property isPublic can only be filtered by owner of userId or admin." +
                    "Can be sorted by id, title, updateDate, creationDate properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(PostPage postPage, PostSearchCriteria postSearchCriteria) {
        boolean isOwner = false;
        boolean isAdmin = authenticationContext.isAdmin();
        try {
            if (postSearchCriteria.getUserId() != null) {
                isOwner = ownerChecker.checkUser(postSearchCriteria.getUserId());
            }
        } catch (Exception ignored) {
        }
        if (!isOwner && !isAdmin) postSearchCriteria.setIsPublic(true);

        Page<Post> responsePost = postService.getAllPosts(postPage, postSearchCriteria);
        Page<PostDto> response = converter.convertToPageDto(responsePost, PostDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new Post.",
            description = "Can be used by User to create a new post.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsUser
    @PostMapping
    public PostDto createPost(@Valid @RequestBody PostCreateDto dto) {
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.createPost(post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    @Operation(
            summary = "Update a Post.",
            description = "Can be used by Owner to update title, description, isPublic, tags properties of own post." +
                    "Can be used by Owner to update title, description, isPublic, tags properties of a post.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPost(#id) || hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public PostDto updatePost(
            @PathVariable(value = "id") @NotNull Long id,
            @Valid @RequestBody PostUpdateDto dto) {
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.updatePost(id, post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    @Operation(
            summary = "Delete a Post.",
            description = "Can be used by Owner to delete own post." +
                    "Can be used by Admin to delete a post.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPost(#id) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") @NotNull Long id) {
        postService.deletePost(id);
    }

    @Operation(
            summary = "Get a Post by it's Id.",
            description = "Can be used to get a public post. " +
                    "Can be used by Owner to get a own private post." +
                    "Can be used by Admin to get a private post.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") @NotNull Long id) {
        boolean isOwner = ownerChecker.checkPost(id);
        boolean isAdmin = authenticationContext.isAdmin();
        Post responsePost = postService.getPostById(id);
        if (responsePost.getIsPublic() || isAdmin || isOwner) {
            return converter.convertToDto(responsePost, PostDto.class);
        } else {
            throw new AccessDeniedException("Access denied to Post by id " + id);
        }
    }
}
