package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.dto.post.PostPage;
import io.github.vbartalis.petshop.dto.post.PostSearchCriteria;
import io.github.vbartalis.petshop.entity.Post;
import org.springframework.data.domain.Page;

/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface PostService {
    /**
     * This method gets a page of the specified {@code Post} entities.
     *
     * @param postPage           The properties of the page returned.
     * @param postSearchCriteria The criteria by which the returned page of entities should be filtered.
     * @return Returns a page of {@code Post} entities.
     */
    Page<Post> getAllPosts(PostPage postPage, PostSearchCriteria postSearchCriteria);

    /**
     * This method creates a {@code Post} entity.
     *
     * @param postRequest The properties of the new {@code Post} entity.
     * @return Returns the created {@code Post} entity.
     */
    Post createPost(Post postRequest);

    /**
     * This method updates the specified {@code Post} entity.
     *
     * @param id          The id of the {@code Post} entity.
     * @param postRequest The properties by which the {@code Post} entity should be updated.
     * @return Returns the updated {@code Post} entity.
     */
    Post updatePost(long id, Post postRequest);

    /**
     * This method deletes the specified {@code Post} entity.
     *
     * @param id The id of the {@code Post} entity.
     */
    void deletePost(long id);

    /**
     * This method gets the specified {@code Post} entity.
     *
     * @param id The id of the {@code Post} entity.
     * @return Returns the specified {@code Post} entity.
     */
    Post getPostById(long id);

}
