package com.example.orderservice.dto;

import java.math.BigDecimal;

public record UserDto(Integer id, String name, BigDecimal balance) {
}
