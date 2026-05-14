package com.example.jpapractice.service;

import com.example.jpapractice.dto.OrderCreateRequest;
import com.example.jpapractice.entity.Member;
import com.example.jpapractice.entity.Order;
import com.example.jpapractice.repository.MemberRepository;
import com.example.jpapractice.repository.OrderRepository;
import com.example.jpapractice.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    //createOrder(OrderCreateRequest request)
    public Long createOrder(OrderCreateRequest request) {

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        Order order = new Order(request.orderNumber(), member);

        Order save = orderRepository.save(order);

        return save.getId();
    }

    public OrderResponse findOrder(Long orderId) {

        Order order = getOrder(orderId);

        return OrderResponse.from(order);
    }

    public List<OrderResponse> findOrderByMember(Long memberId) {
        return orderRepository.findByMemberId(memberId)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional()
    public void cancelOrder(Long orderId) {

        Order order = getOrder(orderId);
        order.cancel();
    }

    private Order getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 주문입니다."));

        return order;
    }


}
