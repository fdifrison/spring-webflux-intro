package org.fdifrison.webflux101;

import org.fdifrison.webflux101.dto.InputFailedValidationResponse;
import org.fdifrison.webflux101.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class L06ExchangeTest extends BaseTest {

    // exchange = retrieve + info (http status code, headers etc.)
    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("r-math/square/{input}/throw", 9)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNext(new InputFailedValidationResponse(100,9,"allowed range is 10- 20"))
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse response) {
        if (response.statusCode() == HttpStatusCode.valueOf(400))
            return response.bodyToMono(InputFailedValidationResponse.class);
        return  response.bodyToMono(Response.class);
    }

}
