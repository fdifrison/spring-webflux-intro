package com.example.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(Integer id, Integer userId, BigDecimal amount, LocalDateTime date) {
}
