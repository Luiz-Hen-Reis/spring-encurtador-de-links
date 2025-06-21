package com.henr.encurtador_links.modules.ShortUrl.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.henr.encurtador_links.modules.ShortUrl.dtos.FetchAllShortUrlsResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@ExtendWith(MockitoExtension.class)
public class FetchAllShortUrlsServiceTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private FetchAllShortUrlsService fetchAllShortUrlsService;

    @Test
    public void should_be_able_to_fetch_all_short_urls() {
        List<ShortUrlEntity> shortUrls = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            var shortUrl = new ShortUrlEntity();
            shortUrl.setShortCode("code-" + i);
            shortUrl.setOriginalUrl("https://original-url-" + i + ".com");
            shortUrl.setExpiresAt(java.time.Instant.now().plusSeconds(3600)); // 1 hour later
            shortUrl.setClicks(0);

            shortUrls.add(shortUrl);
        }

        when(shortUrlRepository.findAll()).thenReturn(shortUrls);

        var response = fetchAllShortUrlsService.execute();

        assertThat(response).isInstanceOf(FetchAllShortUrlsResponseDTO.class);
        assertThat(response.shortUrls()).hasSize(10);
    }
}
