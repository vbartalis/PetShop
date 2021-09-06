package io.github.vbartalis.petshop.exception;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException() {
        super("File is empty.");
    }
}