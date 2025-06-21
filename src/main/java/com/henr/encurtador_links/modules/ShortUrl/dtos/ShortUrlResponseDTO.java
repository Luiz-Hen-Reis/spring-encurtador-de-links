package com.henr.encurtador_links.modules.ShortUrl.dtos;

public record ShortUrlResponseDTO(
    String originalUrl,
    String shortCode
) {}
