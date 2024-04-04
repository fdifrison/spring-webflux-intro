package org.fdifrison.webflux101.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                //                .defaultHeaders(h -> h.setBasicAuth("user", "pwd"))
                .filter(sessionTokenGenerator())
                .build();
    }


    private ExchangeFilterFunction sessionTokenGenerator() {
        System.out.println("Generating Session Token:");
        return (request, next) -> {
            ClientRequest token = ClientRequest.from(request).headers(h -> h.setBearerAuth("jwt-token")).build();
            System.out.println(token.headers());
            return next.exchange(token);
        };


    }


}

