package org.fdifrison.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
public class User {

    @Id
    private Integer id;
    private String name;
    private BigDecimal balance;

}
