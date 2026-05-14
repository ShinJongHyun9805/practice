package com.example.jpapractice.service;

import com.example.jpapractice.dto.OrderCreateRequest;
import com.example.jpapractice.entity.Member;
import com.example.jpapractice.entity.Order;
import com.example.jpapractice.entity.OrderItem;
import com.example.jpapractice.entity.Product;
import com.example.jpapractice.enums.OrderStatus;
import com.example.jpapractice.repository.MemberRepository;
import com.example.jpapractice.repository.OrderItemRepository;
import com.example.jpapractice.repository.OrderRepository;
import com.example.jpapractice.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.sql.init.mode=never")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Create order decreases product stock and saves order item")
    void createOrder() {
        Member member = memberRepository.save(
                new Member("order-test-member", "order-test-member@test.com", 30)
        );
        Product product = productRepository.save(
                new Product("keyboard", 10_000, 10)
        );

        Long orderId = orderService.createOrder(
                new OrderCreateRequest(member.getId(), "ORDER-001", product.getId(), 3)
        );

        Order order = orderRepository.findById(orderId)
                .orElseThrow();
        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow();
        OrderItem orderItem = orderItemRepository.findByOrderId(orderId)
                .getFirst();

        assertThat(order.getOrderNumber()).isEqualTo("ORDER-001");
        assertThat(order.getMember().getId()).isEqualTo(member.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(savedProduct.getStockQuantity()).isEqualTo(7);
        assertThat(orderItem.getOrder().getId()).isEqualTo(orderId);
        assertThat(orderItem.getProduct().getId()).isEqualTo(product.getId());
        assertThat(orderItem.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("Cancel order restores product stock")
    void cancelOrder() {
        Member member = memberRepository.save(
                new Member("cancel-test-member", "cancel-test-member@test.com", 30)
        );
        Product product = productRepository.save(
                new Product("mouse", 20_000, 10)
        );

        Long orderId = orderService.createOrder(
                new OrderCreateRequest(member.getId(), "ORDER-002", product.getId(), 3)
        );

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow();
        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(savedProduct.getStockQuantity()).isEqualTo(10);
    }
}
