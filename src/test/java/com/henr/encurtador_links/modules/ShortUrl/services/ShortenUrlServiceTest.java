package com.henr.encurtador_links.modules.ShortUrl.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.henr.encurtador_links.modules.ShortUrl.dtos.ShortUrlResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@ExtendWith(MockitoExtension.class)
public class ShortenUrlServiceTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private ShortenUrlService shortenUrlService;

    @Test
    public void should_be_able_to_shorten_url() {
       ShortUrlResponseDTO createdShortUrl = shortenUrlService.execute("https://google.com");
       assertThat(createdShortUrl.originalUrl()).isEqualTo("https://google.com");
       assertThat(createdShortUrl.shortCode()).isNotBlank();
    }

    @Test
    public void should_throw_exception_for_invalid_url() {
        String invalidUrl = "htp://invalid-url";
        try {
            shortenUrlService.execute(invalidUrl);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid URL format");
        }
    }
}
