package org.fdifrison.userservice.repository;

import org.fdifrison.userservice.entity.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {

    Flux<Transaction> findAllByUserId(Integer userId);

}
