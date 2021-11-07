package io.github.vbartalis.petshop.exception.custom;

/**
 * This class is a custom exception.
 */
public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("Invalid username.");
    }
}
