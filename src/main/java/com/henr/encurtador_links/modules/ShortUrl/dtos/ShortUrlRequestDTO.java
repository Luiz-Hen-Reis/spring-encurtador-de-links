package com.henr.encurtador_links.modules.ShortUrl.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class ShortUrlRequestDTO {

    @Schema(example = "https://google.com", description = "The original URL to be shortened.")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}