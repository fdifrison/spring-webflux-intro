package org.fdifrison.userservice.controller;

import org.fdifrison.userservice.dto.TransactionRequestDto;
import org.fdifrison.userservice.dto.TransactionResponseDto;
import org.fdifrison.userservice.service.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class TransactionController {

    private final ITransactionService service;

    public TransactionController(ITransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<TransactionResponseDto>> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return service.createTransaction(requestDtoMono).map(request -> ResponseEntity.ok().body(request));
    }
}
