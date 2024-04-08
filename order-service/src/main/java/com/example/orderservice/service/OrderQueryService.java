package com.example.orderservice.service;

import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.repository.PurchaseOrderRepository;
import com.example.orderservice.util.DtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    private final PurchaseOrderRepository orderRepository;

    public OrderQueryService(PurchaseOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<OrderResponseDto> getProductsByUserId(int userId) {
        return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream())
                .map(DtoUtil::getOrderResponse)
                .subscribeOn(Schedulers.boundedElastic()); // since findByUserId is blocking
    }
}
