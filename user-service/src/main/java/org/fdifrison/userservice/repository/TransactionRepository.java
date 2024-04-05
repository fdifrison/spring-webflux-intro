package org.fdifrison.userservice.repository;

import org.fdifrison.userservice.entity.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {
}
