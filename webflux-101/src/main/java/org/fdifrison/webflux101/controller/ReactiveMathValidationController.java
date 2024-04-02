package org.fdifrison.webflux101.controller;

import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.exception.InputValidationException;
import org.fdifrison.webflux101.service.ReactiveMathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("r-math")
public class ReactiveMathValidationController {

    private final ReactiveMathService service;

    public ReactiveMathValidationController(ReactiveMathService service) {
        this.service = service;
    }

    @GetMapping("/square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20)
            throw new InputValidationException(input);
        return service.findSquare(input);
    }

    @GetMapping("/square/{input}/throw-mono")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) ->  {
                    if (integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                })
                .cast(Integer.class)
                .flatMap(service::findSquare);
    }

    @GetMapping("/square/{input}/badReq")
    public Mono<ResponseEntity<Response>> badRequest(@PathVariable int input) {
        return Mono.just(input)
                .filter(i -> i >=10 && i <= 20)
                .flatMap(service::findSquare)
                .map(ResponseEntity::ok)
                // if we are here, it means we are going to emit an onEmpty signal
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
