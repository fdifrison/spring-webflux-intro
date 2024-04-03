package org.fdifrison.webflux101.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

@Component
public class CalculatorHandler {

    // calculator/{a}/{b}
    public Mono<ServerResponse> calculatorHandler(ServerRequest request) {
        String operator = request.headers().asHttpHeaders().toSingleValueMap().get("OP");
        return process(request, (a, b) -> {
            try {
                Integer result = operatorMapper(operator).apply(a, b);
                return ServerResponse.ok().bodyValue(result);
            } catch (ArithmeticException e) {
                return ServerResponse.badRequest().bodyValue("Cant divide by 0");
            } catch (IllegalStateException e) {
                return ServerResponse.badRequest().bodyValue("OP header required. Values in (+, -, *, /). Current value is " + operator);
            }
        });
    }

    private Mono<ServerResponse> process(ServerRequest request,
                                         BiFunction<Integer, Integer, Mono<ServerResponse>> operationLogic) {
        int a = getValue(request, "a");
        int b = getValue(request, "b");
        return operationLogic.apply(a, b);


    }

    private BinaryOperator<Integer> operatorMapper(String operator) {
        if (operator == null) throw  new IllegalStateException();
        return switch (operator) {
            case "+" -> Integer::sum;
            case "-" -> (a, b) -> a - b;
            case "/" -> (a, b) -> a / b;
            case "*" -> (a, b) -> a * b;
            default -> throw new IllegalStateException();
        };
    }

    private int getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }

}
