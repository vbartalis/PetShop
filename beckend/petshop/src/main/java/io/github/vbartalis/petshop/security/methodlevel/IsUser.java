package io.github.vbartalis.petshop.security.methodlevel;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface that can be used as an annotation in controller methods. It checks if the request has the authority of ROLE_USER.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('ROLE_USER')")
public @interface IsUser {
}
