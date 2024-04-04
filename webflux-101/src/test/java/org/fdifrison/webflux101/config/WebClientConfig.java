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
                //                .filter(sessionTokenGenerator())
                .filter(chooseSessionToken())
                .build();
    }

    private ExchangeFilterFunction chooseSessionToken() {
        System.out.println("Choose Session Token:");
        return (request, next) -> {
            // We check for the attribute auth in the request,
            // and if there is we chose the authentication strategy based on its value (basic, bearer)
            // if not, we leave the request as it is
            ClientRequest clientRequest = request.attribute("auth")
                    .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                    .orElse(request);
//            System.out.println(clientRequest.headers());
            return next.exchange(clientRequest);
        };

    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("user", "pwd"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("jwt Token"))
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

