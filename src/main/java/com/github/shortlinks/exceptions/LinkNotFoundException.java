package com.github.shortlinks.exceptions;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException() {
    }

    public LinkNotFoundException(String message) {
        super(message);
    }
}
