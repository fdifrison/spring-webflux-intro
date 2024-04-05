package org.fdifrison.userservice.dto;

import java.math.BigDecimal;

public record UserDto(Integer id, String name, BigDecimal balance) {
}
