package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.Profile;

/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface ProfileService {
    /**
     * This method updates the specified {@code Profile} entity.
     *
     * @param id             The id of the {@code Profile} entity.
     * @param profileRequest The properties by which the {@code Profile} entity should be updated.
     * @return Returns the updated {@code Profile} entity.
     */
    Profile updateProfile(long id, Profile profileRequest);

    /**
     * This method gets the specified {@code Post} entity.
     *
     * @param id The id of the {@code Profile} entity.
     * @return Returns the specified {@code Post} entity.
     */
    Profile getProfileById(long id);
}
