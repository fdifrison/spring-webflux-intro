package org.fdifrison.userservice.util;

import org.fdifrison.userservice.dto.TransactionRequestDto;
import org.fdifrison.userservice.dto.TransactionResponseDto;
import org.fdifrison.userservice.dto.TransactionStatus;
import org.fdifrison.userservice.entity.Transaction;

public class TransactionMapper {

    public static Transaction fromRequestDto (TransactionRequestDto requestDto) {
        return new Transaction(requestDto.userId(), requestDto.amount());
    }

    public static Transaction fromResponseDto (TransactionResponseDto responseDto) {
        return new Transaction(responseDto.userId(), responseDto.amount());
    }

    public static TransactionResponseDto requestToResponse (TransactionRequestDto requestDto, TransactionStatus status) {
        return new TransactionResponseDto(requestDto.userId(), requestDto.amount(), status);
    }


}
