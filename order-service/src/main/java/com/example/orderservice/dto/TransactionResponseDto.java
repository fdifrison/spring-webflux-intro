package com.example.orderservice.dto;

import java.math.BigDecimal;

public record TransactionResponseDto(Integer userId, BigDecimal amount, TransactionStatus status) {
}
