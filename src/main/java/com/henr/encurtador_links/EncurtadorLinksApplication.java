package com.henr.encurtador_links;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Short Link API",
		version = "1.0.0",
		description = "API for managing short links"
	)
)
public class EncurtadorLinksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncurtadorLinksApplication.class, args);
	}

}
