package com.example.jpapractice.response;

import com.example.jpapractice.entity.Order;
import com.example.jpapractice.enums.OrderStatus;

public record OrderResponse(

        Long orderId,
        String orderNumber,
        OrderStatus orderStatus,
        Long memberId,
        String memberName
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderStatus(),
                order.getMember().getId(),
                order.getMember().getName()
        );
    }
}
