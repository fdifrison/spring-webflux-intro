package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderRequestDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.RequestContext;
import com.example.orderservice.repository.PurchaseOrderRepository;
import com.example.orderservice.util.DtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {

    private final UserClient userClient;
    private final ProductClient productClient;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderFulfillmentService(UserClient userClient, ProductClient productClient,
                                   PurchaseOrderRepository purchaseOrderRepository) {
        this.userClient = userClient;
        this.productClient = productClient;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Mono<OrderResponseDto> purchaseOrder(Mono<OrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContext::new)
                // from the request we call the product client to retrieve the product that the user wants to buy and we
                // save its specifics inside the requestContext
                .flatMap(this::productRequestResponse)
                // given the product information we build the transaction dto, and we insert it inside the requestContext
                .doOnNext(DtoUtil::setTransactionRequestDto)
                // similarly we query the user-service to retrieve information about the user, if he has enough money
                // to fulfil the purchase order
                .flatMap(this::userRequestResponse)
                // now we can build the purchase order and set its status based on the transaction response status
                .map(DtoUtil::getPurchaseOrder)
                // we can save in the current service the purchase order
                .map(purchaseOrderRepository::save) // blocking driver
                // and finally return the orderResponse dto to the user
                .map(DtoUtil::getOrderResponse)
                // we need to spawn a new thread since
                // we have a blocking operation in the pipeline (we could have also used publishOn before the
                // blocking ::save operation
                .subscribeOn(Schedulers.boundedElastic());

    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext) {
        return this.productClient.getProductById(requestContext.getOrderRequest().productId())
                .doOnNext(requestContext::setProduct)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(requestContext);

    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return this.userClient.authorizeTransaction(requestContext.getTransactionRequest())
                .doOnNext(requestContext::setTransactionResponse)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(requestContext);
    }
}
