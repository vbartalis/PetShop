package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.ProfileImage;
import io.github.vbartalis.petshop.exception.FileTypeNotSupportedException;
import io.github.vbartalis.petshop.exception.ImageWriterException;
import io.github.vbartalis.petshop.repository.ProfileImageRepository;
import io.github.vbartalis.petshop.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class ProfileImageService {

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Autowired
    ProfileRepository profileRepository;

    public ProfileImage findById(Long id) {
        return profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
    }

    public ProfileImage patch(Long profileImageId, MultipartFile multipartFile) {
        ProfileImage profileImage = profileImageRepository.findById(profileImageId)
                .orElseThrow(() -> new NoSuchElementException("ProfileImage by id " + profileImageId + " not found"));
        
        if (!Objects.equals(multipartFile.getContentType(), "image/jpeg")) {
            log.warn("!Objects.equals(multipartFile.getContentType(), \"image/jpeg\")");
            throw new FileTypeNotSupportedException();
        }
        
        try {
            profileImage.setData(multipartFile.getBytes());
            profileImageRepository.save(profileImage);
        } catch (IOException e) {
            log.warn("ImageWriterException()");
            throw new ImageWriterException();
        }
        return profileImage;
    }

    public void deleteData(Long id) {
        ProfileImage profileImage = profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
        profileImage.setData(null);
        profileImageRepository.save(profileImage);
    }
}
