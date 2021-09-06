package io.github.vbartalis.petshop.exception;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException() {
        super("Username already in use.");
    }
}
