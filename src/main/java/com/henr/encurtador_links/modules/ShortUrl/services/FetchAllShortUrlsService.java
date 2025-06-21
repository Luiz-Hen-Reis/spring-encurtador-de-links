package com.henr.encurtador_links.modules.ShortUrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henr.encurtador_links.modules.ShortUrl.dtos.FetchAllShortUrlsResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@Service
public class FetchAllShortUrlsService {
    
    @Autowired
    private ShortUrlRepository shortUrlRepository;;

    public FetchAllShortUrlsResponseDTO execute() {
       return new FetchAllShortUrlsResponseDTO(this.shortUrlRepository.findAll());
    }
}
