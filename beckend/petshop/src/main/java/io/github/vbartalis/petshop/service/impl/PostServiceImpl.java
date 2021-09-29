package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.dto.post.PostPage;
import io.github.vbartalis.petshop.dto.post.PostSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.repository.criteriaRepository.PostCriteriaRepository;
import io.github.vbartalis.petshop.repository.entityRepository.PostRepository;
import io.github.vbartalis.petshop.repository.entityRepository.UserRepository;
import io.github.vbartalis.petshop.service.PostService;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

/**
 * This is a service class that implements {@code PostService}. Its methods are used to service the {@code PostController}
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostCriteriaRepository postCriteriaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationContext authenticationContext;

    /**
     * This method is a {@code getAllPosts} implementation.
     */
    @Override
    public Page<Post> getAllPosts(PostPage postPage, PostSearchCriteria postSearchCriteria) {
        return postCriteriaRepository.findAllWithFilters(postPage, postSearchCriteria);
    }

    /**
     * This method is a {@code createPost} implementation.
     */
    @Override
    public Post createPost(Post postRequest) {
        var username = authenticationContext.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User by username " + username + " not found"));
        var title = postRequest.getTitle();
        var description = postRequest.getDescription();
        var creationDate = new Date();
        var isPublic = postRequest.getIsPublic();
        var tags = postRequest.getTags();
        Post newPost = new Post(title, description, user, creationDate, isPublic, tags);

        postRepository.save(newPost);
        return postRequest;
    }

    /**
     * This method is a {@code updatePost} implementation.
     */
    @Override
    public Post updatePost(long id, Post postRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setUpdateDate(new Date());
        post.setTags(postRequest.getTags());
        postRepository.save(post);
        return post;
    }

    /**
     * This method is a {@code deletePost} implementation.
     */
    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));
        postRepository.delete(post);
    }

    /**
     * This method is a {@code getPostById} implementation.
     */
    @Override
    public Post getPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + id + " not found"));
    }
}
