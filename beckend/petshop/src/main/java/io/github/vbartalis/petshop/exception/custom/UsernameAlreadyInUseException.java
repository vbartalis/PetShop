package io.github.vbartalis.petshop.exception.custom;

/**
 * This class is a custom exception.
 */
public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException() {
        super("Username already in use.");
    }
}
