package com.henr.encurtador_links.modules.ShortUrl.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henr.encurtador_links.modules.ShortUrl.entities.ShortUrlEntity;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    Optional<ShortUrlEntity> findByShortCode(String shortCode);
}
