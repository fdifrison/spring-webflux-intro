package org.fdifrison.webflux101;

import org.fdifrison.webflux101.dto.MultiplyRequest;
import org.fdifrison.webflux101.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class L04HeadersTest extends BaseTest {


    @Test
    public void headerTest() {

        Mono<Response> responseMono = this.webClient
                .post()
                .uri("r-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .headers(httpHeaders -> httpHeaders.set("authorization", "admin"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.output() == 10)
                .verifyComplete();
    }

    private MultiplyRequest buildRequestDto(int a, int b) {
        return new MultiplyRequest(a, b);
    }

}
