package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequestDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.service.OrderFulfillmentService;
import com.example.orderservice.service.OrderQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    private final OrderFulfillmentService service;

    private final OrderQueryService queryService;

    public PurchaseOrderController(OrderFulfillmentService service, OrderQueryService queryService) {
        this.service = service;
        this.queryService = queryService;
    }

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDto>> create(@RequestBody Mono<OrderRequestDto> orderRequestDtoMono) {
        return this.service.purchaseOrder(orderRequestDtoMono)
                .map(response -> ResponseEntity.ok().body(response))
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class,
                        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Flux<OrderResponseDto>> getOrdersByUserID(@PathVariable Integer userId) {
        Flux<OrderResponseDto> productsByUserId = this.queryService.getProductsByUserId(userId);
        return ResponseEntity.ok().body(productsByUserId);
    }


}
