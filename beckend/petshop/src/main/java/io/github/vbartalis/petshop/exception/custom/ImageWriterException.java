package io.github.vbartalis.petshop.exception.custom;

/**
 * This class is a custom exception.
 */
public class ImageWriterException extends RuntimeException {
    public ImageWriterException() {
        super("Something went wrong with the image writing.");
    }
}
