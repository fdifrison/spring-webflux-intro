package com.example.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public final class RequestContext {
    private OrderRequestDto orderRequest;
    private ProductDto product;
    private TransactionRequestDto transactionRequest;
    private TransactionResponseDto transactionResponse;

    public RequestContext(OrderRequestDto orderRequest) {
        this.orderRequest = orderRequest;
    }
}
