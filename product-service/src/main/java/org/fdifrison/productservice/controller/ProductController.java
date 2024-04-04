package org.fdifrison.productservice.controller;

import org.fdifrison.productservice.dto.ProductDto;
import org.fdifrison.productservice.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @GetMapping("all")
    public Flux<ProductDto> getAllProducts() {
        return this.service.getAllProducts();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> findById(@PathVariable String id) {
        return this.service.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.service.insertProduct(productDtoMono);
    }

    @PutMapping("{id}")
    public Mono<Boolean> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> dtoMono) {
        return this.service.updateProduct(id, dtoMono);
    }

    @DeleteMapping("{id}")
    public Mono<Boolean> deleteProduct(@PathVariable String id) {
        return this.service.deleteProduct(id);
    }

}
