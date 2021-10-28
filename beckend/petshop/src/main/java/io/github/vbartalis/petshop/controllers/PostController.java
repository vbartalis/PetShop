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

/**
 * This class serves as RestController.
 */
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

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns a page of {@code PostDto} that is filtered by the provided criteria.
     *
     * @param postPageCriteria   The properties of the page returned.
     * @param postSearchCriteria The criteria by which the returned page of entities should be filtered.
     * @return Returns a page of {@code PostDto}.
     */

    @Operation(
            summary = "Get all Posts.",
            description = "Can be filtered by id, title, description, userId properties. " +
                    "Property isPublic can only be filtered by owner of userId or admin. " +
                    "Can be sorted by id, title, updateDate, creationDate properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(PostPageCriteria postPageCriteria, PostSearchCriteria postSearchCriteria) {
        boolean isOwner = false;
        boolean isAdmin = authenticationContext.isAdmin();
        try {
            if (postSearchCriteria.getUserId() != null) {
                isOwner = ownerChecker.checkUser(postSearchCriteria.getUserId());
            }
        } catch (Exception ignored) {
        }
        if (!isOwner && !isAdmin) postSearchCriteria.setIsPublic(true);

        Page<Post> responsePost = postService.getAllPosts(postPageCriteria, postSearchCriteria);
        Page<PostDto> response = converter.convertToPageDto(responsePost, PostDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a POST request.
     * It creates a new {@code Post} Entity.
     *
     * @param dto The Data Transfer Object that contains the properties of the new {@code Post} Entity.
     * @return Returns the created {@code Post} Entity in the form of {@code PostDto}.
     */
    @Operation(
            summary = "Create a new Post.",
            description = "Can be used by User to create a new post." +
                    "Expired or locked users an not use it.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @IsUser
    @PostMapping
    public PostDto createPost(@Valid @RequestBody PostCreateDto dto) {
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.createPost(post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a PUT request.
     * It updates the specified {@code Post} entity.
     *
     * @param id  The id of the {@code Post} entity.
     * @param dto The Data Transfer Object that contains the properties of the {@code Post}
     *            Entity that needs to be updated.
     * @return Returns the updated {@code Post} Entity in the form of {@code PostDto}.
     */
    @Operation(
            summary = "Update a Post.",
            description = "Can be used by Owner to update title, description, isPublic, tags properties of own post." +
                    "Expired or locked users an not use it." +
                    "Can be used by Admin to update title, description, isPublic, tags properties of a post.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize(" @ownerChecker.checkPost(#id) || hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public PostDto updatePost(
            @PathVariable(value = "id") @NotNull Long id,
            @Valid @RequestBody PostUpdateDto dto) {
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.updatePost(id, post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a DELETE request.
     * It deletes the specified {@code Post} entity.
     *
     * @param id The id of the {@code Post} entity.
     */
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

    /**
     * This method serves as a REST Service Endpoint.
     * <p>
     * This endpoint can be accessed by a GET request.
     * It returns the specified {@code Post} entity in the form of {@code PostDto}.
     *
     * @param id The id of the {@code Post} entity.
     * @return Returns the specified {@code Post} Entity in the form of {@code PostDto}.
     */
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
