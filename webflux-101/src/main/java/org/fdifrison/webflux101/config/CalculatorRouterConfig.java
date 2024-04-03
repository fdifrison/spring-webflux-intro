package org.fdifrison.webflux101.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculatorRouterConfig {

    private final CalculatorHandler handler;

    public CalculatorRouterConfig(CalculatorHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> calculatorRouter() {
        return RouterFunctions.route()
                .GET("calculator/{a}/{b}", handler::calculatorHandler)
                .onError(RuntimeException.class, (e, request) -> ServerResponse.badRequest().bodyValue(e))
                .build();
    }

}
