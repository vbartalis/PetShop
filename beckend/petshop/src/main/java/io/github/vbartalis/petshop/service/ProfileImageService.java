package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {

//    Page<ProfileImage> getAllProfileImages();

//    ProfileImage createProfileImage(ProfileImage profileImageRequest);

    ProfileImage updateProfileImage(long id, MultipartFile multipartFile);

    void deleteProfileImage(long id);

    ProfileImage getProfileImageById(long id);
}
