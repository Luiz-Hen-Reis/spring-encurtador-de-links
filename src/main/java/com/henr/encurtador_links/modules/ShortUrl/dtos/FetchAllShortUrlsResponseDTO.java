package com.henr.encurtador_links.modules.ShortUrl.dtos;

import java.util.List;

import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;

public record FetchAllShortUrlsResponseDTO(
    List<ShortUrlEntity> shortUrls
    ) {}
