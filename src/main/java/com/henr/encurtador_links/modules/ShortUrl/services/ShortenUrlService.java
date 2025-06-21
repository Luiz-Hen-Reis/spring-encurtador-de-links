package com.henr.encurtador_links.modules.ShortUrl.services;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henr.encurtador_links.modules.ShortUrl.dtos.ShortUrlResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@Service
public class ShortenUrlService {
    
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public ShortUrlResponseDTO execute(String originalUrl) {
        ShortUrlEntity shortUrl = new ShortUrlEntity();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(generateShortCode());
        shortUrl.setClicks(0);
        shortUrl.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));

        shortUrlRepository.save(shortUrl);

        return new ShortUrlResponseDTO(shortUrl.getOriginalUrl(), shortUrl.getShortCode());
    }


    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}