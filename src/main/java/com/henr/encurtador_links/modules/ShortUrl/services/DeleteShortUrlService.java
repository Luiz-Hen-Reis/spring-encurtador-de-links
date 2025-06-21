package com.henr.encurtador_links.modules.ShortUrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henr.encurtador_links.exceptions.ShortUrlNotFoundException;
import com.henr.encurtador_links.modules.ShortUrl.dtos.DeleteShortUrlResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@Service
public class DeleteShortUrlService {
    
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public DeleteShortUrlResponseDTO execute(String shortCode) {
        var shortUrl = this.shortUrlRepository.findByShortCode(shortCode)
            .orElseThrow(() -> new ShortUrlNotFoundException());

        this.shortUrlRepository.delete(shortUrl);

        return new DeleteShortUrlResponseDTO("Short URL deleted successfully");
    }
}
