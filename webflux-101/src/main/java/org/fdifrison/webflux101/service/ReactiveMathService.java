package org.fdifrison.webflux101.service;

import org.fdifrison.webflux101.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    // to have a reactive behavior is not enough to just return a Flux,
    // we need
    // to do the computation inside the pipeline
    // to leverage all the async and non-blocking capabilities of the framework
    public Flux<Response> findMultiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1)) // non-blocking sleeps
                .doOnNext(i -> System.out.println("ReactiveMathService processing -> " + i))
                .map(i -> i * input)
                .map(Response::new);
    }

}
