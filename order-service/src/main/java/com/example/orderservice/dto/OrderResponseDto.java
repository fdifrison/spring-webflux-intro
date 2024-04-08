package com.example.orderservice.dto;

import java.math.BigDecimal;

public record OrderResponseDto(Integer orderId, Integer userId, String productId, BigDecimal amount,
        OrderStatus status) {
}
