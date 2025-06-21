package com.henr.encurtador_links.modules.ShortUrl.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henr.encurtador_links.exceptions.ShortUrlHasExpiredException;
import com.henr.encurtador_links.exceptions.ShortUrlNotFoundException;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@Service
public class RedirectToOriginalService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;
    
    public String execute(String shortCode) {
        var shortUrl = this.shortUrlRepository.findByShortCode(shortCode)
        .orElseThrow(() -> new ShortUrlNotFoundException());

        if (Instant.now().isAfter(shortUrl.getExpiresAt())) {
            throw new ShortUrlHasExpiredException();
        }

        shortUrl.setClicks(shortUrl.getClicks() + 1);
        this.shortUrlRepository.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }
}
