package org.fdifrison.productservice.service;

import org.fdifrison.productservice.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


public interface IProductService {

    Flux<ProductDto> getAllProducts();

    Mono<ProductDto> getProductById(String id);

    Flux<ProductDto> getProductsByPrice(BigDecimal price);

    Flux<ProductDto> getProductsByPriceBetween(BigDecimal min, BigDecimal max);
    Flux<ProductDto> getProductsByPriceRange(BigDecimal min, BigDecimal max);
    Mono<ProductDto> insertProduct(Mono<ProductDto> dtoMono);

    Mono<Boolean> updateProduct(String id, Mono<ProductDto> productDtoMono);

    Mono<Boolean> deleteProduct(String id);

}
