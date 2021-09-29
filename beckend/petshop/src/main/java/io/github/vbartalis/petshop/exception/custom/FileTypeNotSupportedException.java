package io.github.vbartalis.petshop.exception.custom;

/**
 * This class is a custom exception.
 */
public class FileTypeNotSupportedException extends RuntimeException {
    public FileTypeNotSupportedException() {
        super("File type not supported.");
    }
}
