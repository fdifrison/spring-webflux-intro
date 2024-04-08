package com.example.orderservice.dto;

import java.math.BigDecimal;

public record TransactionRequestDto(Integer userId, BigDecimal amount) {
}
