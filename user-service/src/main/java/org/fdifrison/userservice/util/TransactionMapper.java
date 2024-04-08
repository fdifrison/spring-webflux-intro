package org.fdifrison.userservice.util;

import org.fdifrison.userservice.dto.TransactionDto;
import org.fdifrison.userservice.dto.TransactionRequestDto;
import org.fdifrison.userservice.dto.TransactionResponseDto;
import org.fdifrison.userservice.dto.TransactionStatus;
import org.fdifrison.userservice.entity.Transaction;

import java.time.LocalDateTime;

public class TransactionMapper {

    public static Transaction fromRequestDto (TransactionRequestDto requestDto) {
        return new Transaction(requestDto.userId(), requestDto.amount(), LocalDateTime.now());
    }

    public static Transaction fromResponseDto (TransactionResponseDto responseDto) {
        return new Transaction(responseDto.userId(), responseDto.amount(), LocalDateTime.now());
    }

    public static TransactionResponseDto requestToResponse (TransactionRequestDto requestDto, TransactionStatus status) {
        return new TransactionResponseDto(requestDto.userId(), requestDto.amount(), status);
    }

    public static TransactionDto toDto (Transaction t) {
        return new TransactionDto(t.getId(), t.getUserId(), t.getAmount(), t.getTransactionDate());
    }


}
