package com.henr.encurtador_links.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.henr.encurtador_links.exceptions.dtos.ExceptionResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleShortUrlNotFoundException(ShortUrlNotFoundException ex) {
        var response = new ExceptionResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ShortUrlHasExpiredException.class)
    public ResponseEntity<ExceptionResponseDTO> handleShortUrlHasExpiredException(ShortUrlHasExpiredException ex) {
        var response = new ExceptionResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.GONE).body(response);
    }
}
