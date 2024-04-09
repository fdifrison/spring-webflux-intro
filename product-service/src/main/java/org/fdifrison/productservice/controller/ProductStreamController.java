package org.fdifrison.productservice.controller;

import org.fdifrison.productservice.dto.ProductDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@RestController
@RequestMapping("product")
public class ProductStreamController {

    private final Flux<ProductDto> productStream;

    public ProductStreamController(Flux<ProductDto> productStream) {
        this.productStream = productStream;
    }

    @GetMapping(value = "stream/{maxPrice}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE) // needed to become a streaming api
    public Flux<ProductDto> getProductsUpdate(@PathVariable BigDecimal maxPrice) {
        return productStream
                .filter(dto -> dto.price().compareTo(maxPrice) < 0);
    }


}
