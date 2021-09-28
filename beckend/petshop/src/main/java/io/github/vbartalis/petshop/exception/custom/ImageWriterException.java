package io.github.vbartalis.petshop.exception.custom;


public class ImageWriterException extends RuntimeException {
    public ImageWriterException() {
        super("Something went wrong with the image writing.");
    }
}
