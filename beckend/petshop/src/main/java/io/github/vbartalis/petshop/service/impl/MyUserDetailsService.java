package io.github.vbartalis.petshop.service.impl;

import io.github.vbartalis.petshop.repository.entityRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * This is a service class that implements {@code UserDetailsService}.
 */
@Component
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    /**
     * This method is a {@code loadUserByUsername} implementation.
     *
     * @param username The username of the user.
     * @return Returns the UserDetails of the specified user.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Username: " + username + " not found"));
    }


}
