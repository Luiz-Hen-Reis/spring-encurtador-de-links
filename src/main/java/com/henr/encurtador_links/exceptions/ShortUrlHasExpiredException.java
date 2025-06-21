package com.henr.encurtador_links.exceptions;

public class ShortUrlHasExpiredException extends RuntimeException {

    public ShortUrlHasExpiredException() {
        super("Short URL has expired");
    }

}
