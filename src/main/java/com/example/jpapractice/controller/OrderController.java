package com.example.jpapractice.controller;

import com.example.jpapractice.dto.OrderCreateRequest;
import com.example.jpapractice.response.OrderResponse;
import com.example.jpapractice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Long createOrder(OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.findOrder(orderId);
    }

    @GetMapping
    public List<OrderResponse> getOrders(@RequestParam Long memberId) {
        return orderService.findOrderByMember(memberId);
    }

    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }
}
