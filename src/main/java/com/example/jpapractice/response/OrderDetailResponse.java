package com.example.jpapractice.response;

import com.example.jpapractice.entity.Order;
import com.example.jpapractice.entity.OrderItem;
import com.example.jpapractice.enums.OrderStatus;

import java.util.List;

public record OrderDetailResponse(

        Long orderId,
        String orderNumber,
        OrderStatus orderStatus,
        Long memberId,
        String memberName,
        List<OrderItemResponse> orderitems
) {

    public static OrderDetailResponse of(Order order, List<OrderItem> orderItems) {
        return new OrderDetailResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderStatus(),
                order.getMember().getId(),
                order.getMember().getName(),
                orderItems.stream()
                        .map(OrderItemResponse::from)
                        .toList()
        );
    }
}
