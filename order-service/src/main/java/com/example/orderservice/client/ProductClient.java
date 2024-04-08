package com.example.orderservice.client;

import com.example.orderservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public Mono<ProductDto> getProductById(final String id) {
        return this.webClient
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
