package org.fdifrison.webflux101.config;

import org.fdifrison.webflux101.dto.InputFailedValidationResponse;
import org.fdifrison.webflux101.exception.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    private final RequestHandler handler;

    public RouterConfig(RequestHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", RequestPredicates.path("*/1?")
                        .or(RequestPredicates.path("*/20")), handler::squareHandler)
                .GET("router/square/{input}",
                        request -> ServerResponse.badRequest().bodyValue("not valid input [10-20]"))
                .GET("router/squareValid/{input}", handler::squareHandlerWithValidation)
                .GET("router/table/{input" + "}", handler::tableHandler)
                .GET("router/tableStream/{input}", handler::tableStreamHandler)
                .POST("router/multiply", handler::multiplyHandler)
                .onError(InputValidationException.class, exceptionHandler()).build();
    }

    // works as a controllerAdvice
    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse(ex.getInput(),
                    ex.getErrorCode(), ex.getMessage());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }

}
