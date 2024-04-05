package org.fdifrison.userservice.dto;

import java.math.BigDecimal;

public record TransactionRequestDto(Integer userId, BigDecimal amount) {
}
