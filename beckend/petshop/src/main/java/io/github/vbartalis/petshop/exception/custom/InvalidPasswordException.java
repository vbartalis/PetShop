package io.github.vbartalis.petshop.exception.custom;

/**
 * This class is a custom exception.
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password.");
    }
}
