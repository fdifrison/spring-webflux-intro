package org.fdifrison.webflux101.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    private final RequestHandler handler;

    public RouterConfig(RequestHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", handler::squareHandler)
                .GET("router/table/{input}", handler::tableHandler)
                .GET("router/tableStream/{input}", handler::tableStreamHandler)
                .build();
    }

}
