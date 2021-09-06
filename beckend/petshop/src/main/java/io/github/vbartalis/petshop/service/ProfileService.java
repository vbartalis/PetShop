package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Profile;
import io.github.vbartalis.petshop.repository.ProfileRepository;
import io.github.vbartalis.petshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

    public Profile findByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by userId " + id + " not found"))
                .getProfile();
    }

    public Profile findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Profile by username " + username + " not found"))
                .getProfile();
    }

    public Profile findById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + id + " not found"));
    }

    public Profile patch(Profile profile) {
        Profile updatedProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + profile.getId() + " not found"));

        if (profile.getName() != null)
            updatedProfile.setName(profile.getName());
        if (profile.getEmail() != null)
            updatedProfile.setEmail(profile.getEmail());
        if (profile.getAddress() != null)
            updatedProfile.setAddress(profile.getAddress());
        if (profile.getDescription() != null)
            updatedProfile.setDescription(profile.getDescription());

        profileRepository.save(updatedProfile);
        return updatedProfile;
    }
}
