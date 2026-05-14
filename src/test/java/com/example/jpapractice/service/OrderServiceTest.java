package com.example.jpapractice.service;

import com.example.jpapractice.dto.OrderCreateRequest;
import com.example.jpapractice.entity.Member;
import com.example.jpapractice.entity.Order;
import com.example.jpapractice.enums.OrderStatus;
import com.example.jpapractice.repository.MemberRepository;
import com.example.jpapractice.repository.OrderRepository;
import com.example.jpapractice.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("회원은 주믄을 생성할 수 있다.")
    void createOrder() {

        Member member = memberRepository.save(
                new Member("kim", "kim@test.com", 30)
        );

        Long orderId = orderService.createOrder(
                new OrderCreateRequest(member.getId(), "ORDER-001")
        );

        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        assertThat(order.getOrderNumber()).isEqualTo("ORDER-001");
        assertThat(order.getMember().getId()).isEqualTo(member.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CREATED);
    }


    @Test
    @DisplayName("회원 ID로 주문 목록을 조회할 수 있다")
    void findOrdersByMember() {
        Member member = memberRepository.save(
                new Member("kim", "kim@test.com", 30)
        );

        orderService.createOrder(
                new OrderCreateRequest(member.getId(), "ORDER-001")
        );

        orderService.createOrder(
                new OrderCreateRequest(member.getId(), "ORDER-002")
        );

        List<OrderResponse> responses =
                orderService.findOrderByMember(member.getId());

        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("주문을 취소하면 상태가 CANCELED가 된다")
    void cancelOrder() {
        Member member = memberRepository.save(
                new Member("kim", "kim@test.com", 30)
        );

        Long orderId = orderService.createOrder(
                new OrderCreateRequest(member.getId(), "ORDER-001")
        );

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }
}
