package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Profile;

public interface ProfileService {

//    Page<Profile> getAllProfiles();

//    Profile createProfile(Profile profileRequest);

    Profile updateProfile(long id, Profile profileRequest);

//    void deleteProfile(long id);

    Profile getProfileById(long id);
}
