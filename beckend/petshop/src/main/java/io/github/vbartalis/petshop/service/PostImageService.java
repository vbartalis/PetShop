package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.PostImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface PostImageService {

    /**
     * This method updates the specified {@code PostImage} entity.
     *
     * @param id            The id of the {@code PostImage} entity.
     * @param multipartFile The multipart file that is the actual image.
     * @return Returns the updated {@code PostImage} entity.
     */
    PostImage updatePostImage(long id, MultipartFile multipartFile);

    /**
     * This method deletes the data property of the specified {@code PostImage} entity.
     *
     * @param id The id of the {@code PostImage} entity.
     */
    void deletePostImage(long id);

    /**
     * This method gets the specified {@code PostImage} entity.
     *
     * @param id The id of the {@code PostImage} entity.
     * @return Returns the specified {@code PostImage} entity.
     */
    PostImage getPostImageById(long id);
}
