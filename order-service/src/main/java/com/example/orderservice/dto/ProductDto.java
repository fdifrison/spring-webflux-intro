package com.example.orderservice.dto;

import java.math.BigDecimal;

public record ProductDto(String id, String description, BigDecimal price) {
}
