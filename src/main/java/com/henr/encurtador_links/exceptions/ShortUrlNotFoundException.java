package com.henr.encurtador_links.exceptions;

public class ShortUrlNotFoundException extends RuntimeException {

    public ShortUrlNotFoundException() {
        super("Short URL not found");
    }

}
