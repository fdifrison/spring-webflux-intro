package com.example.orderservice.util;

import com.example.orderservice.dto.*;
import com.example.orderservice.entity.PurchaseOrder;

public class DtoUtil {

    public static void setTransactionRequestDto(RequestContext rc) {
        TransactionRequestDto requestDto = new TransactionRequestDto(rc.getOrderRequest().userId(),
                rc.getProduct().price());
        rc.setTransactionRequest(requestDto);

    }

    public static PurchaseOrder getPurchaseOrder(RequestContext rc) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(rc.getOrderRequest().userId());
        purchaseOrder.setProductId(rc.getProduct().id());
        purchaseOrder.setAmount(rc.getProduct().price());

        if (rc.getTransactionResponse().status().equals(TransactionStatus.APPROVED)) {
            purchaseOrder.setStatus(OrderStatus.COMPLETED);
        } else {
            purchaseOrder.setStatus(OrderStatus.FAILED);
        }

        return purchaseOrder;
    }

    public static OrderResponseDto getOrderResponse(PurchaseOrder purchaseOrder) {
        return new OrderResponseDto(purchaseOrder.getId(), purchaseOrder.getUserId(),
                purchaseOrder.getProductId(), purchaseOrder.getAmount(), purchaseOrder.getStatus());
    }

}
