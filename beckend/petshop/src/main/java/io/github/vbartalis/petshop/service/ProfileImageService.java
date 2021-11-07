package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface ProfileImageService {

    /**
     * This method updates the specified {@code ProfileImage} entity.
     *
     * @param id            The id of the {@code ProfileImage} entity.
     * @param multipartFile The multipart file that is the actual image.
     * @return Returns the updated {@code ProfileImage} entity.
     */
    ProfileImage updateProfileImage(long id, MultipartFile multipartFile);

    /**
     * This method deletes the data property of the specified {@code ProfileImage} entity.
     *
     * @param id The id of the {@code ProfileImage} entity.
     */
    void deleteProfileImage(long id);

    /**
     * This method gets the specified {@code ProfileImage} entity.
     *
     * @param id The id of the {@code ProfileImage} entity.
     * @return Returns the specified {@code ProfileImage} entity.
     */
    ProfileImage getProfileImageById(long id);
}
