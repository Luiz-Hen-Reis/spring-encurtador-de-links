package com.henr.encurtador_links.modules.ShortUrl.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.henr.encurtador_links.exceptions.ShortUrlNotFoundException;
import com.henr.encurtador_links.modules.ShortUrl.dtos.DeleteShortUrlResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteShortUrlServiceTest {
    
    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private DeleteShortUrlService deleteShortUrlService;

    @Test
    public void should_not_be_able_to_delete_non_existent_short_url() {
        assertThrows(ShortUrlNotFoundException.class, () -> deleteShortUrlService.execute("nonexistent-code"));
    }

    @Test
    public void should_be_able_to_delete_an_short_url() {
        String shortCode = "valid-code";

        var shortUrl = new ShortUrlEntity();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl("https://valid-url.com");
        shortUrl.setExpiresAt(java.time.Instant.now().plusSeconds(3600)); // 1 hour later
        shortUrl.setClicks(0);

        when(shortUrlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(shortUrl));

        var response = deleteShortUrlService.execute(shortCode);

        assertThat(response).isInstanceOf(DeleteShortUrlResponseDTO.class);
        assertThat(response.message()).isEqualTo("Short URL deleted successfully");
    }
}
