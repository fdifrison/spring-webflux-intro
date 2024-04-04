package org.fdifrison.productservice.service;

import org.fdifrison.productservice.dto.ProductDto;
import org.fdifrison.productservice.repository.ProductRepository;
import org.fdifrison.productservice.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<ProductDto> getAllProducts() {
        return repository.findAll().map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<ProductDto> getProductById(String id) {
        return repository.findById(id).map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<ProductDto> insertProduct(Mono<ProductDto> dtoMono) {
        return dtoMono
                .map(EntityDtoMapper::toEntity)
                .flatMap(this.repository::insert)
                .map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<Boolean> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return this.repository.findById(id)
                .flatMap(p -> productDtoMono.map(EntityDtoMapper::toEntity).doOnNext(e -> e.setId(id)))
                .flatMap(this.repository::save)
                .map(product -> true);

    }

    @Override
    public Mono<Boolean> deleteProduct(String id) {
        return this.repository.deleteById(id).then(Mono.fromCallable(() -> true));
    }
}
