package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.post.PatchPostDto;
import io.github.vbartalis.petshop.dto.post.PostDto;
import io.github.vbartalis.petshop.dto.post.PostPostDto;
import io.github.vbartalis.petshop.dto.post.SearchPublicPostDto;
import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.security.methodlevel.IsUser;
import io.github.vbartalis.petshop.service.PostService;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "get Post By Post Id",description = "Can be used by Public, Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/{id}")
    public PostDto getPost(@PathVariable("id") Long id) {
        Post responsePost = postService.findById(id);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    //todo order by creation date or update date
    @Operation(summary = "get Public Posts By Criteria (Tag Ids), and page number ")
    @GetMapping
    public Page<PostDto> getPublicPosts(
//            @RequestParam(value = "dto") List<Long> dto,
            SearchPublicPostDto dto,
            @Min(value = 1) @RequestParam(value = "page") int page
    ) {
        int pageSize = 10;
        Page<Post> postPage = postService.findAllPublicByCriteria(dto.getIds(), page, pageSize);
//        Page<Post> postPage = postService.findAllPublicByCriteria(dto, page, pageSize);
        return converter.convertToPageDto(postPage, PostDto.class);
    }

    //todo order by creation date or update date
    @Operation(summary = "get Posts By User Id", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkUser(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public Page<PostDto> getUserPosts(
            @PathVariable("id") Long id,
            @Min(value = 1) @RequestParam(value = "page") int page
    ) {
        int pageSize = 10;
        Page<Post> postPage = postService.findByUser(id, page, pageSize);
        return converter.convertToPageDto(postPage, PostDto.class);
    }

    @Operation(summary = "create a new Post", description = "Can be used by User", security = @SecurityRequirement(name = "bearerAuth"))
    @IsUser
    @PostMapping
    public PostDto postPost(@Valid @RequestBody PostPostDto dto) {
//        log.warn("should be validated");
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.save(post);
        return converter.convertToDto(responsePost, PostDto.class);
    }


    @Operation(summary = "update a Post", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPost(#dto.id, authentication) || hasAuthority('ROLE_ADMIN')")
    @PatchMapping
    public PostDto patchPost(@Valid @RequestBody PatchPostDto dto) {
//        log.warn("should be validated");
//        log.warn(Json.pretty(dto));
        Post post = converter.convertToEntity(dto, Post.class);
        Post responsePost = postService.patch(post);
        return converter.convertToDto(responsePost, PostDto.class);
    }

    @Operation(summary = "delete a Post", description = "Can be used by Owner or Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("@ownerChecker.checkPost(#id, authentication) || hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
    }

}
