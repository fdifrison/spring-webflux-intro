package org.fdifrison.webflux101.config;

import org.fdifrison.webflux101.dto.MultiplyRequest;
import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.exception.InputValidationException;
import org.fdifrison.webflux101.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RequestHandler {

    private final ReactiveMathService service;

    public RequestHandler(ReactiveMathService service) {
        this.service = service;
    }

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> square = service.findSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> multiplicationTable = service.findMultiplicationTable(input);
        return ServerResponse.ok().body(multiplicationTable, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> multiplicationTable = service.findMultiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(multiplicationTable, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequest> multiplyRequestMono = serverRequest.bodyToMono(MultiplyRequest.class);
        Mono<Response> multiply = service.multiply(multiplyRequestMono);
        return ServerResponse.ok().body(multiply, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> square = service.findSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }
}
