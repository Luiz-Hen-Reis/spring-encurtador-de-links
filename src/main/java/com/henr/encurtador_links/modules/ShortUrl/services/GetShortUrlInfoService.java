package com.henr.encurtador_links.modules.ShortUrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henr.encurtador_links.exceptions.ShortUrlNotFoundException;
import com.henr.encurtador_links.modules.ShortUrl.dtos.GetShortUrlInfoResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@Service
public class GetShortUrlInfoService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public GetShortUrlInfoResponseDTO execute(String shortCode) {
        var shortUrl = this.shortUrlRepository.findByShortCode(shortCode)
            .orElseThrow(() -> new ShortUrlNotFoundException());

        return new GetShortUrlInfoResponseDTO(shortUrl);
    }
}
