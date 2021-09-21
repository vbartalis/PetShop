package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.ProfileImage;
import io.github.vbartalis.petshop.exception.FileTypeNotSupportedException;
import io.github.vbartalis.petshop.exception.ImageWriterException;
import io.github.vbartalis.petshop.repository.entityRepository.ProfileImageRepository;
import io.github.vbartalis.petshop.service.ProfileImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class ProfileImageServiceImpl implements ProfileImageService {

    @Autowired
    ProfileImageRepository profileImageRepository;

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

    @Override
    public void deleteProfileImage(long id) {
        ProfileImage profileImage = profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
        profileImage.setData(null);
        profileImageRepository.save(profileImage);
    }

    @Override
    public ProfileImage getProfileImageById(long id) {
        return profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
    }
}
