package com.henr.encurtador_links.modules.ShortUrl.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.henr.encurtador_links.exceptions.ShortUrlHasExpiredException;
import com.henr.encurtador_links.exceptions.ShortUrlNotFoundException;
import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;
import com.henr.encurtador_links.modules.ShortUrl.repositories.ShortUrlRepository;

@ExtendWith(MockitoExtension.class)
public class RedirectToOriginalServiceTest {
    
    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private RedirectToOriginalService redirectToOriginalService;

    @Test
    public void should_not_be_able_to_redirect_to_original_url_if_short_url_does_not_exist() {
        assertThrows(ShortUrlNotFoundException.class,
         () -> redirectToOriginalService.execute("nonexistent-code"));
    }

    @Test
    public void should_not_be_able_to_redirect_to_original_url_if_short_url_is_expired() {
        var expiredShortUrl = new ShortUrlEntity();
        expiredShortUrl.setShortCode("expired-code");
        expiredShortUrl.setOriginalUrl("https://expired-url.com");
        expiredShortUrl.setExpiresAt(java.time.Instant.now().minusSeconds(3600)); // 1 hour ago
        expiredShortUrl.setClicks(0);

        when(shortUrlRepository.findByShortCode("expired-code"))
            .thenReturn(Optional.of(expiredShortUrl));

        assertThrows(ShortUrlHasExpiredException.class,
         () -> redirectToOriginalService.execute("expired-code"));
    }

    @Test
    public void should_be_able_to_redirect_to_original_url() {
        var validShortUrl = new ShortUrlEntity();
        validShortUrl.setShortCode("valid-code");
        validShortUrl.setOriginalUrl("https://valid-url.com");
        validShortUrl.setExpiresAt(java.time.Instant.now().plusSeconds(3600)); // 1 hour later
        validShortUrl.setClicks(0);

        when(shortUrlRepository.findByShortCode("valid-code"))
            .thenReturn(Optional.of(validShortUrl));

        var result = redirectToOriginalService.execute("valid-code");

        assertEquals("https://valid-url.com", result);
    }
}
