package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.entity.Profile;
import io.github.vbartalis.petshop.repository.entityRepository.ProfileRepository;
import io.github.vbartalis.petshop.repository.entityRepository.UserRepository;
import io.github.vbartalis.petshop.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * This is a service class that implements {@code ProfileService}. Its methods are used to service the {@code ProfileController}
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * This method is a {@code updateProfile} implementation.
     */
    @Override
    public Profile updateProfile(long id, Profile postRequest) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));

        profile.setName(postRequest.getName());
        profile.setEmail(postRequest.getEmail());
        profile.setAddress(postRequest.getAddress());
        profile.setDescription(postRequest.getDescription());

        profileRepository.save(profile);
        return profile;
    }

    /**
     * This method is a {@code getProfileById} implementation.
     */
    @Override
    public Profile getProfileById(long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
    }
}
