package org.fdifrison.userservice.service;

import org.fdifrison.userservice.dto.TransactionDto;
import org.fdifrison.userservice.dto.TransactionRequestDto;
import org.fdifrison.userservice.dto.TransactionResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ITransactionService {

    Mono<TransactionResponseDto> createTransaction(Mono<TransactionRequestDto> requestDto);

    Flux<TransactionDto> getTransactionsByUserId(Integer userId);



}
