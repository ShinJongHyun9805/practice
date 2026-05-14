package com.example.jpapractice.response;

import com.example.jpapractice.entity.OrderItem;

public record OrderItemResponse(
        Long orderItemId,
        Long productId,
        String productName,
        int orderPrice,
        int quantity,
        int totalPrice
) {

    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getOrderPrice(),
                orderItem.getQuantity(),
                orderItem.getTotalPrice()
        );
    }
}