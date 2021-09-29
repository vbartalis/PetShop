package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.dto.user.UserPage;
import io.github.vbartalis.petshop.dto.user.UserSearchCriteria;
import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.exception.custom.InvalidPasswordException;
import io.github.vbartalis.petshop.exception.custom.InvalidUsernameException;
import io.github.vbartalis.petshop.exception.custom.UsernameAlreadyInUseException;
import io.github.vbartalis.petshop.repository.criteriaRepository.UserCriteriaRepository;
import io.github.vbartalis.petshop.repository.entityRepository.UserRepository;
import io.github.vbartalis.petshop.service.UserService;
import io.github.vbartalis.petshop.util.InputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCriteriaRepository userCriteriaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUsers(UserPage userPage, UserSearchCriteria userSearchCriteria) {
        return userCriteriaRepository.findAllWithFilters(userPage, userSearchCriteria);
    }

    @Override
    public User createUser(User userRequest) {
        if (!InputValidator.isUsernameValid(userRequest.getUsername())) throw new InvalidUsernameException();
        if (!InputValidator.isPasswordValid(userRequest.getPassword())) throw new InvalidPasswordException();
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) throw new UsernameAlreadyInUseException();

        User newUser = new User(
                userRequest.getUsername(),
                this.passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getIsLocked(),
                userRequest.getExpiration(),
                userRequest.getRoles()
        );
        this.userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User partialUpdateUser(long id, User userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id " + id + " not found"));

//        password is not null and not blank
        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            //password is valid
            if (!InputValidator.isPasswordValid(userRequest.getPassword())) throw new InvalidPasswordException();
            user.setPassword(this.passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getIsLocked() != null) user.setIsLocked(userRequest.getIsLocked());
        if (userRequest.getExpiration() != null) user.setExpiration(userRequest.getExpiration());
        if (userRequest.getRoles() != null) user.setRoles(userRequest.getRoles());

        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id " + id + " not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User by username " + username + " not found"));
    }
}
