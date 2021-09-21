package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.dto.request.PostPage;
import io.github.vbartalis.petshop.dto.request.PostSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<Post> getAllPosts(PostPage postPage, PostSearchCriteria postSearchCriteria);

    Post createPost(Post postRequest);

    Post updatePost(long id, Post postRequest);

    void deletePost(long id);

    Post getPostById(long id);

}
