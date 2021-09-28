package io.github.vbartalis.petshop.exception.custom;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("Invalid username.");
    }
}
