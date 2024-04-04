package org.fdifrison.webflux101;

import org.fdifrison.webflux101.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class L01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {

        Response response = this.webClient
                .get()
                .uri("r-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class) // Mono<Response>
                .block();

        System.out.println(response);

    }


    @Test
    public void stepVerifierTest() {

        Mono<Response> response = this.webClient
                .get()
                .uri("r-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.output() == 25)
                .verifyComplete();

        System.out.println(response.block());

    }


}
