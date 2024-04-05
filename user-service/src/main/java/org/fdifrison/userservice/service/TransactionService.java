package org.fdifrison.userservice.service;

import org.fdifrison.userservice.dto.TransactionRequestDto;
import org.fdifrison.userservice.dto.TransactionResponseDto;
import org.fdifrison.userservice.dto.TransactionStatus;
import org.fdifrison.userservice.repository.TransactionRepository;
import org.fdifrison.userservice.repository.UserRepository;
import org.fdifrison.userservice.util.TransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
class TransactionService implements ITransactionService {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    TransactionService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Mono<TransactionResponseDto> createTransaction(Mono<TransactionRequestDto> requestDto) {
        return requestDto.flatMap(req -> this.userRepository
                .updateUserBalance(req.userId(), req.amount())
                .filter(Boolean::booleanValue)
                .map(updated -> TransactionMapper.fromRequestDto(req))
                .flatMap(this.transactionRepository::save)
                .map(transaction -> TransactionMapper.requestToResponse(req, TransactionStatus.APPROVED))
                .defaultIfEmpty(TransactionMapper.requestToResponse(req, TransactionStatus.DECLINED))
        );

    }
}
