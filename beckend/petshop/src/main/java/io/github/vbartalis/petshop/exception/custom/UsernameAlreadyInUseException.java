package io.github.vbartalis.petshop.exception.custom;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException() {
        super("Username already in use.");
    }
}
