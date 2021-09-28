package io.github.vbartalis.petshop.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This class contains methods that can give information about the current user.
 */
@Component
public class AuthenticationContext {

    /**
     * This method gets the Authentication of the current user.
     * @return Returns the current users Authentication.
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * This method gets the username of the current user.
     * @return Returns the username of the current user.
     */
    public String getUsername() {
        return getAuthentication().getName();
    }

    /**
     * This method checks if the current user has the authority of ROLE_ADMIN.
     * @return Returns true if the current user has the authority of ROLE_ADMIN, otherwise false.
     */
    public Boolean isAdmin() {
        return getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
