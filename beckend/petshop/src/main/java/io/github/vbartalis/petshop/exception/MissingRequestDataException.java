package io.github.vbartalis.petshop.exception;

public class MissingRequestDataException extends RuntimeException {
    public MissingRequestDataException(String s) {
        super(s);
    }
}
