package com.example.jpapractice.controller;

import com.example.jpapractice.dto.OrderCreateRequest;
import com.example.jpapractice.response.OrderDetailResponse;
import com.example.jpapractice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Long createOrder(@RequestBody @Valid OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.findOrderDetail(orderId);
    }

    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }
}
