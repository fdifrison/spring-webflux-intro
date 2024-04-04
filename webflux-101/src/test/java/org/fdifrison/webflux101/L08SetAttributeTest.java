package org.fdifrison.webflux101;

import org.fdifrison.webflux101.dto.MultiplyRequest;
import org.fdifrison.webflux101.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class L08SetAttributeTest extends BaseTest {

    @Test
    public void headerBasicAuthTest() {


        Mono<Response> responseMono = this.webClient
                .post()
                .uri("r-math/multiply")
                .bodyValue(buildRequestDto(10, 3))
                .attribute("auth", "basic")
                .attributes(System.out::println)
                .exchangeToMono(response -> {
                    System.out.println(response.request().getHeaders());
                    return response.bodyToMono(Response.class);
                })
                .doOnNext(System.out::println);


        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.output() == 30)
                .verifyComplete();
    }

    private MultiplyRequest buildRequestDto(int a, int b) {
        return new MultiplyRequest(a, b);
    }
}
