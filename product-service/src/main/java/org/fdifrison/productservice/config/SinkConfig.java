package org.fdifrison.productservice.config;

import org.fdifrison.productservice.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<ProductDto> sink() {
        return Sinks
                .many() // many items to emit
                .replay() // repeat for later subscriber
                .limit(1); // repeat last item
    }

    @Bean
    public Flux<ProductDto> products(Sinks.Many<ProductDto> sink) { // injected
        return sink.asFlux();
    }

}
