package org.fdifrison.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Integer id;
    private String name;
    private BigDecimal balance;

}
