package org.fdifrison.userservice.repository;

import org.fdifrison.userservice.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    @Modifying // important notation to methods that don't return the modified entity
    @Query("update users set balance = balance - :amount where id = :userId and balance >= :amount")
    Mono<Boolean> updateUserBalance(int userId, BigDecimal amount);
}
