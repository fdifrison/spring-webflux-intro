package org.fdifrison.userservice.service;

import org.fdifrison.userservice.dto.TransactionRequestDto;
import org.fdifrison.userservice.dto.TransactionResponseDto;
import reactor.core.publisher.Mono;


public interface ITransactionService {

    public Mono<TransactionResponseDto> createTransaction(Mono<TransactionRequestDto> requestDto);


}
