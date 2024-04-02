package org.fdifrison.webflux101.controller;

import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("r-math")
public class ReactiveMathController {

    // The subscriber will be the browser, hence we don't need to call subscribe on the publisher but simply return it

    private final ReactiveMathService service;

    public ReactiveMathController(ReactiveMathService service) {
        this.service = service;
    }

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return service.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public Flux<Response> findMultiplicationTable(@PathVariable int input) {
        return this.service.findMultiplicationTable(input);
    }

    // as a stream, we send values to the browser as soon as they are ready
    @GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> findMultiplicationTableStream(@PathVariable int input) {
        return this.service.findMultiplicationTable(input);
    }

}
