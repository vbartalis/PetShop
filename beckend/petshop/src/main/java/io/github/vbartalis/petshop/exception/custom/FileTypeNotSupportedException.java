package io.github.vbartalis.petshop.exception.custom;

public class FileTypeNotSupportedException extends RuntimeException {
    public FileTypeNotSupportedException() {
        super("File type not supported.");
    }
}
