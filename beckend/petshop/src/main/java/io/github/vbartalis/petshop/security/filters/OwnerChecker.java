package io.github.vbartalis.petshop.security.filters;

import io.github.vbartalis.petshop.repository.entityRepository.*;
import io.github.vbartalis.petshop.util.AuthenticationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * This Class contains methods that can check weather the current user is the owner of the requested resource or not.
 */
@Slf4j
@Component
public class OwnerChecker {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostImageRepository postImageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ProfileImageRepository profileImageRepository;

    @Autowired
    AuthenticationContext authenticationContext;

    /**
     * This method checks weather the current user is the owner of the specified Post entity.
     *
     * @param postId The Id of the Post entity.
     * @return Returns true if the current user is the owner, otherwise false.
     */
    public boolean checkPost(Long postId) {
        String username = postRepository
                .findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + postId + " not found"))
                .getUser()
                .getUsername();
        return checkLoggedInUser(username);
    }

    /**
     * This method checks weather the current user is the owner of the specified PostImage entity.
     *
     * @param imageId The Id of the PostImage entity.
     * @return Returns true if the current user is the owner, otherwise false.
     */
    public boolean checkPostImage(Long imageId) {
        String username = postImageRepository
                .findById(imageId)
                .orElseThrow(() -> new NoSuchElementException("PostImage by id " + imageId + " not found"))
                .getPost()
                .getUser()
                .getUsername();
        return checkLoggedInUser(username);
    }

    /**
     * This method checks weather the current user is the owner of the specified User entity.
     *
     * @param id The Id of the User entity.
     * @return Returns true if the current user is the owner, otherwise false.
     */
    public boolean checkUser(Long id) {
        String username = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id " + id + " not found"))
                .getUsername();
        return checkLoggedInUser(username);
    }

    /**
     * This method checks weather the current user is the owner of the specified Profile entity.
     *
     * @param profileId The Id of the Profile entity.
     * @return Returns true if the current user is the owner, otherwise false.
     */
    public boolean checkProfile(Long profileId) {
        String username = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + profileId + " not found"))
                .getUser()
                .getUsername();
        return checkLoggedInUser(username);
    }

    /**
     * This method checks weather the current user is the owner of the specified ProfileImage entity.
     *
     * @param profileImageId The Id of the ProfileImage entity.
     * @return Returns true if the current user is the owner, otherwise false.
     */
    public boolean checkProfileImage(Long profileImageId) {
        String username = profileImageRepository
                .findById(profileImageId)
                .orElseThrow(() -> new NoSuchElementException("ProfileImage by id " + profileImageId + " not found"))
                .getProfile()
                .getUser()
                .getUsername();
        return checkLoggedInUser(username);
    }

    /**
     * This method checks weather the provided username is the username of the current user.
     *
     * @param username The provided username.
     * @return Returns true if the provided username is the username of the current user, otherwise false.
     */
    private boolean checkLoggedInUser(String username) {
        Authentication authentication = authenticationContext.getAuthentication();
        return username.equals(authentication.getPrincipal()) && !username.equals("anonymousUser");
    }


}
