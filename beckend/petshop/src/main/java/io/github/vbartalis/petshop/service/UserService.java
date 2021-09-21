package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.dto.request.PostPage;
import io.github.vbartalis.petshop.dto.request.PostSearchCriteria;
import io.github.vbartalis.petshop.dto.request.UserPage;
import io.github.vbartalis.petshop.dto.request.UserSearchCriteria;
import io.github.vbartalis.petshop.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<User> getAllUsers(UserPage userPage, UserSearchCriteria userSearchCriteria);

    User createUser(User userRequest);

    User partialUpdateUser(long id, User userRequest);

//    void deleteUser(long id);

    User getUserById(long id);

    User getUserByUsername(String username);
}
