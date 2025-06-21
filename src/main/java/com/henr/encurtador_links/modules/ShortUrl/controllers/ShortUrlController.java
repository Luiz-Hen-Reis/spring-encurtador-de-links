package com.henr.encurtador_links.modules.ShortUrl.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.henr.encurtador_links.exceptions.dtos.ExceptionResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.dtos.DeleteShortUrlResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.dtos.FetchAllShortUrlsResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.dtos.GetShortUrlInfoResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.dtos.ShortUrlRequestDTO;
import com.henr.encurtador_links.modules.ShortUrl.dtos.ShortUrlResponseDTO;
import com.henr.encurtador_links.modules.ShortUrl.services.DeleteShortUrlService;
import com.henr.encurtador_links.modules.ShortUrl.services.FetchAllShortUrlsService;
import com.henr.encurtador_links.modules.ShortUrl.services.GetShortUrlInfoService;
import com.henr.encurtador_links.modules.ShortUrl.services.RedirectToOriginalService;
import com.henr.encurtador_links.modules.ShortUrl.services.ShortenUrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
public class ShortUrlController {
    
    @Autowired
    private ShortenUrlService shortenUrlService;

    @Autowired
    private RedirectToOriginalService redirectToOriginalService;

    @Autowired
    private FetchAllShortUrlsService fetchAllShortUrlsService;

    @Autowired
    private GetShortUrlInfoService getShortUrlInfoService;

    @Autowired
    private DeleteShortUrlService deleteShortUrlService;

    @PostMapping("/shorten")
    @Tag(name = "Short URL", description = "Endpoints for managing short URLs")
    @Operation(
        summary = "Create Short URL",
        description = "This endpoint allows you to create a short URL by providing the original URL."
    )
    @ApiResponse(responseCode = "201")
    public ResponseEntity<ShortUrlResponseDTO> shorten(@RequestBody ShortUrlRequestDTO dto) {
        var result = this.shortenUrlService.execute(dto.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{shortCode}")
    @Tag(name = "Short URL")
    @Operation(
        summary = "Redirect to Original URL",
        description = "This endpoint redirects the user to the original URL based on the provided short code."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "302", 
            description = "Redirects to the original URL"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Short URL not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ExceptionResponseDTO.class,
                                example = "{\"message\": \"Short URL not found\"}")
            )
        ),
        @ApiResponse(
            responseCode = "410",
            description = "Short URL has expired",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ExceptionResponseDTO.class,
                                example = "{\"message\": \"Short URL has expired\"}")
            )
        )
    })
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        var originalUrl = this.redirectToOriginalService.execute(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/links")
    @Tag(name = "Short URL")
    @Operation(
        summary = "Fetch All Short URLs",
        description = "This endpoint retrieves all short URLs."
    )
    public ResponseEntity<FetchAllShortUrlsResponseDTO> fetchAll() {
        return ResponseEntity.ok(this.fetchAllShortUrlsService.execute());
    }

    @GetMapping("/links/{shortCode}")
    @Tag(name = "Short URL")
    @Operation(
        summary = "Fetch Short URL Info",
        description = "This endpoint retrieves information about a specific short URL."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Short URL deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Short URL not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ExceptionResponseDTO.class,
                                example = "{\"message\": \"Short URL not found\"}")
            )
        )
    })
    public ResponseEntity<GetShortUrlInfoResponseDTO> getInfo(@PathVariable String shortCode)
    {
        var shortUrlInfo = this.getShortUrlInfoService.execute(shortCode);
        return ResponseEntity.ok(shortUrlInfo);
    }

    @DeleteMapping("/links/{shortCode}")
    @Tag(name = "Short URL")
    @Operation(
        summary = "Delete Short URL",
        description = "This endpoint deletes a specific short URL."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Short URL deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Short URL not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ExceptionResponseDTO.class,
                                example = "{\"message\": \"Short URL not found\"}")
            )
    )
    })
    public ResponseEntity<DeleteShortUrlResponseDTO> delete(@PathVariable String shortCode)
    {
        var message = this.deleteShortUrlService.execute(shortCode);
        return ResponseEntity.ok(message);
    }
}
    