package io.github.vbartalis.petshop.exception;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("Invalid username.");
    }
}
