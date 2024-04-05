package org.fdifrison.userservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
public class Transaction {

    @Id
    private Integer id;
    private Integer userId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public Transaction(Integer integer, BigDecimal amount) {
        this.transactionDate = LocalDateTime.now();
    }
}
