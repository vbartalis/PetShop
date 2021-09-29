package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.dto.user.UserPage;
import io.github.vbartalis.petshop.dto.user.UserSearchCriteria;
import io.github.vbartalis.petshop.entity.User;
import org.springframework.data.domain.Page;

/**
 * This interface defines how a service should look. The implementation should service a controller class.
 */
public interface UserService {
    /**
     * This method gets a page of the specified {@code User} entities.
     *
     * @param userPage           The properties of the page returned.
     * @param userSearchCriteria The criteria by which the returned page of entities should be filtered.
     * @return Returns a page of {@code User} entities.
     */
    Page<User> getAllUsers(UserPage userPage, UserSearchCriteria userSearchCriteria);

    /**
     * This method creates a {@code User} entity.
     *
     * @param userRequest The properties of the new {@code User} entity.
     * @return Returns the created {@code User} entity.
     */
    User createUser(User userRequest);

    /**
     * This method updates the specified {@code User} entity.
     *
     * @param id          The id of the {@code User} entity.
     * @param userRequest The properties by which the {@code User} entity should be updated.
     * @return Returns the updated {@code User} entity.
     */
    User partialUpdateUser(long id, User userRequest);

    /**
     * This method gets the specified {@code User} entity.
     *
     * @param id The id of the {@code User} entity.
     * @return Returns the specified {@code User} entity.
     */
    User getUserById(long id);

    /**
     * This method gets the specified {@code User} entity.
     *
     * @param username The username of the {@code User} entity.
     * @return Returns the specified {@code User} entity.
     */
    User getUserByUsername(String username);
}
