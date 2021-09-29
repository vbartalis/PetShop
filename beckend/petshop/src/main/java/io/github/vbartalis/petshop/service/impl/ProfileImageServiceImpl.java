package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.ProfileImage;
import io.github.vbartalis.petshop.exception.custom.FileTypeNotSupportedException;
import io.github.vbartalis.petshop.exception.custom.ImageWriterException;
import io.github.vbartalis.petshop.repository.entityRepository.ProfileImageRepository;
import io.github.vbartalis.petshop.service.ProfileImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This is a service class that implements {@code ProfileImageService}. Its methods are used to service the {@code ProfileImageController}
 */
@Slf4j
@Service
public class ProfileImageServiceImpl implements ProfileImageService {

    @Autowired
    ProfileImageRepository profileImageRepository;

    /**
     * This method is a {@code updateProfileImage} implementation.
     */
    @Override
    public ProfileImage updateProfileImage(long id, MultipartFile multipartFile) {
        ProfileImage profileImage = profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ProfileImage by id " + id + " not found"));

        if (!Objects.equals(multipartFile.getContentType(), "image/jpeg")) {
            throw new FileTypeNotSupportedException();
        }

        try {
            profileImage.setData(multipartFile.getBytes());
            profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new ImageWriterException();
        }
        return profileImage;
    }

    /**
     * This method is a {@code deleteProfileImage} implementation.
     */
    @Override
    public void deleteProfileImage(long id) {
        ProfileImage profileImage = profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
        profileImage.setData(null);
        profileImageRepository.save(profileImage);
    }

    /**
     * This method is a {@code getProfileImageById} implementation.
     */
    @Override
    public ProfileImage getProfileImageById(long id) {
        ProfileImage profileImage = profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ProfileImage by id " + id + " not found"));
        if (profileImage.getData() == null) throw new NoSuchElementException("ProfileImage by id " + id + " not found");
        else return profileImage;
    }
}
