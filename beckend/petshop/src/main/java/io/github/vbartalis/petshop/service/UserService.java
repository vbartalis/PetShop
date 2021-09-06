package io.github.vbartalis.petshop.service;

import io.github.vbartalis.petshop.entity.User;
import io.github.vbartalis.petshop.exception.InvalidPasswordException;
import io.github.vbartalis.petshop.exception.InvalidUsernameException;
import io.github.vbartalis.petshop.exception.UsernameAlreadyInUseException;
import io.github.vbartalis.petshop.repository.ProfileImageRepository;
import io.github.vbartalis.petshop.repository.ProfileRepository;
import io.github.vbartalis.petshop.repository.RoleRepository;
import io.github.vbartalis.petshop.repository.UserRepository;
import io.github.vbartalis.petshop.util.InputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileImageRepository profileImageRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (!InputValidator.isUsernameValid(user.getUsername())) throw new InvalidUsernameException();
        if (!InputValidator.isPasswordValid(user.getPassword())) throw new InvalidPasswordException();
        if (userRepository.findByUsername(user.getUsername()).isPresent()) throw new UsernameAlreadyInUseException();
        User newUser = new User(
                user.getUsername(),
                this.passwordEncoder.encode(user.getPassword()),
                user.getIsLocked(),
                user.getExpiration(),
                user.getRoles()
        );
        this.userRepository.save(newUser);
        return newUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id " + id + " not found"));
    }

    public Page<User> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return userRepository.findAll(pageable);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User by username " + username + " not found"));
    }

    public User patch(User request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException("User by id " + request.getId() + " not found"));

        //isLocked is not null
        if (request.getIsLocked() != null) {
            user.setIsLocked(request.getIsLocked());
        }

        //expiration is not null
        if (request.getExpiration() != null) {
            user.setExpiration(request.getExpiration());
        }

        //roles is not null
        if (request.getRoles() != null) {
            user.setRoles(request.getRoles());
        }

        userRepository.save(user);
        return user;
    }

    public User patchPassword(User request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException("User by id " + request.getId() + " not found"));

        //password is not null and not blank
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            //password is valid
            if (!InputValidator.isPasswordValid(request.getPassword())) throw new InvalidPasswordException();
            user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return user;
    }
}
