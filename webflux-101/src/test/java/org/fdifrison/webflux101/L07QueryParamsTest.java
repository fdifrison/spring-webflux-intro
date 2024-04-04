package org.fdifrison.webflux101;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class L07QueryParamsTest extends BaseTest {

    @Test
    public void queryParamsTest() {

        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/jobs/search?count={count}&page={page}")
                .build(10, 20);

        Flux<Integer> integerFlux = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void queryParamsUriBuilderTest() {


        Flux<Integer> integerFlux = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }


    @Test
    public void queryParamsMapsTest() {

        Map<String, Integer> params = Map.of("count", 10,
                "page", 20);


        Flux<Integer> integerFlux = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(params))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }


}
