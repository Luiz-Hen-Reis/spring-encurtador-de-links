package com.henr.encurtador_links.modules.ShortUrl.dtos;

import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;

public record GetShortUrlInfoResponseDTO(
    ShortUrlEntity shortUrl
) {}
