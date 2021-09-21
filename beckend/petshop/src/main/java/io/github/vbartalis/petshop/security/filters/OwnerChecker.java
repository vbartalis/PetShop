package io.github.vbartalis.petshop.security.filters;

import io.github.vbartalis.petshop.repository.entityRepository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

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

    public boolean checkPost(Long postId, Authentication authentication) {
        String username = postRepository
                .findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post by id " + postId + " not found"))
                .getUser()
                .getUsername();
        return checkLoggedInUser(username, authentication);
    }

    public boolean checkPostImage(Long imageId, Authentication authentication) {
        String username = postImageRepository
                .findById(imageId)
                .orElseThrow(() -> new NoSuchElementException("PostImage by id " + imageId + " not found"))
                .getPost()
                .getUser()
                .getUsername();
        return checkLoggedInUser(username, authentication);
    }

    public boolean checkUser(Long id, Authentication authentication) {
        String username = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id " + id + " not found"))
                .getUsername();
        return checkLoggedInUser(username, authentication);
    }
    public boolean checkUser(String username, Authentication authentication) {
        return checkLoggedInUser(username, authentication);
    }
    public boolean checkProfile(Long profileId, Authentication authentication) {
        String username = postRepository
                .findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Profile by id " + profileId + " not found"))
                .getUser()
                .getUsername();
        return checkLoggedInUser(username, authentication);
    }

    public boolean checkProfileImage(Long profileImageId, Authentication authentication) {
        String username = profileImageRepository
                .findById(profileImageId)
                .orElseThrow(() -> new NoSuchElementException("ProfileImage by id " + profileImageId + " not found"))
                .getProfile()
                .getUser()
                .getUsername();
        return checkLoggedInUser(username, authentication);
    }

    private boolean checkLoggedInUser(String username, Authentication authentication) {
        return username.equals(authentication.getPrincipal()) && !username.equals("anonymousUser");
    }
}
