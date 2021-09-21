package io.github.vbartalis.petshop.controllers;

import io.github.vbartalis.petshop.dto.post.PostDto;
import io.github.vbartalis.petshop.dto.request.Filter;
import io.github.vbartalis.petshop.dto.request.Pagination;
import io.github.vbartalis.petshop.dto.test.PostPage;
import io.github.vbartalis.petshop.dto.test.PostSearchCriteria;
import io.github.vbartalis.petshop.dto.user.UserDto;
import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.security.methodlevel.IsAdmin;
import io.github.vbartalis.petshop.security.methodlevel.IsUser;
import io.github.vbartalis.petshop.service.PostService;
import io.github.vbartalis.petshop.service.TestService;
import io.github.vbartalis.petshop.util.DtoEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    DtoEntityConverter converter;

    @Operation(summary = "get Users", description = "Can be used by Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @IsAdmin
    @GetMapping(value = "/user")
    public Page<UserDto> getUsers(@Min(value = 1) @RequestParam(value = "page") int page) {
        int pageSize = 10;
        Page<User> users = testService.findAll(page, pageSize);
        return converter.convertToPageDto(users, UserDto.class);
    }

//    @GetMapping(value = "/post1")
//    public ResponseEntity<Page<PostDto>> getPosts1(@RequestParam(required = false) PostPage postPage, @RequestParam(required = false) PostSearchCriteria postSearchCriteria) {
//
//        if (Objects.nonNull(postPage)) log.warn("postPage: ", postPage.toString());
//        else log.warn("postPage: null");
//        if (Objects.nonNull(postSearchCriteria)) log.warn("postSearchCriteria: ", postSearchCriteria.toString());
//        else log.warn("postSearchCriteria: null");
//
//        Page<Post> responsePost = testService.getPosts(postPage, postSearchCriteria);
//        Page<PostDto> response =  converter.convertToPageDto(responsePost, PostDto.class);;
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping(value = "/post2")
    public ResponseEntity<Page<PostDto>> getPosts2(PostPage postPage, PostSearchCriteria postSearchCriteria) {

        Page<Post> responsePost = testService.getPosts(postPage, postSearchCriteria);
        Page<PostDto> response =  converter.convertToPageDto(responsePost, PostDto.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<Post> addPost(@RequestBody Post post){
        return new ResponseEntity<>(testService.addPost(post), HttpStatus.OK);
    }




}
