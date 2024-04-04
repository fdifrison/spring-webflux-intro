package org.fdifrison.webflux101;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

public class L09AssignmentTest extends BaseTest {

    private static final String FORMAT = "%d %s %d = %s"; // operation = result
    private static final int A = 10;

    @Test
    public void testCalculator() {
        Flux<String> flux = Flux.range(1, 5)
                .flatMap(b -> Flux.just("+", "-", "*", "/")
                        .flatMap(op -> send(b, op)))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(20)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }

    private Mono<String> send(int b, String op) {
        return this.webClient
                .get()
                .uri("calculator/{a}/{b}", A, b)
                .headers(h -> h.set("OP", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(s -> String.format(FORMAT, A, op, b, s));

    }

}
