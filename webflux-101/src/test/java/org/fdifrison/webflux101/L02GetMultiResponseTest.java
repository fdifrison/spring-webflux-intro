package org.fdifrison.webflux101;

import org.fdifrison.webflux101.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class L02GetMultiResponseTest extends BaseTest {


    @Test
    public void fluxStepVerifierTest() {

        Flux<Response> response = this.webClient
                .get()
                .uri("r-math/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();


    }

    @Test
    public void fluxStreamStepVerifierTest() {

        Flux<Response> response = this.webClient
                .get()
                .uri("r-math/table/{input}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();


    }

}
