package org.fdifrison.productservice.repository;

import org.fdifrison.productservice.dto.ProductDto;
import org.fdifrison.productservice.entity.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<ProductDto> findProductsByPriceBetween(BigDecimal price, BigDecimal price2);

    @Query("{ 'price': { $gte: ?0, $lte: ?1 } }")
    Flux<ProductDto> findProductsByPriceRange(BigDecimal min, BigDecimal max);

    Flux<ProductDto> findProductsByPrice(BigDecimal price);
}
