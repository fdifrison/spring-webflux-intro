package org.fdifrison.productservice.service;

import org.fdifrison.productservice.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IProductService {

    Flux<ProductDto> getAllProducts();

    Mono<ProductDto> getProductById(String id);

    Mono<ProductDto> insertProduct(Mono<ProductDto> dtoMono);

    Mono<Boolean> updateProduct(String id, Mono<ProductDto> productDtoMono);

    Mono<Boolean> deleteProduct(String id);
}
