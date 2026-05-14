package com.example.jpapractice.service;

import com.example.jpapractice.dto.OrderCreateRequest;
import com.example.jpapractice.entity.Member;
import com.example.jpapractice.entity.Order;
import com.example.jpapractice.entity.OrderItem;
import com.example.jpapractice.entity.Product;
import com.example.jpapractice.repository.MemberRepository;
import com.example.jpapractice.repository.OrderItemRepository;
import com.example.jpapractice.repository.OrderRepository;
import com.example.jpapractice.repository.ProductRepository;
import com.example.jpapractice.response.OrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createOrder(OrderCreateRequest request) {

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 상품입니다."));

        Order order = new Order(request.orderNumber(), member);
        Order save = orderRepository.save(order);

        OrderItem orderItem = new OrderItem(save, product, request.quantity());
        orderItemRepository.save(orderItem);

        return save.getId();
    }

    public OrderDetailResponse findOrderDetail(Long orderId) {

        Order order = getOrder(orderId);
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        return OrderDetailResponse.of(order, orderItems);
    }

    @Transactional()
    public void cancelOrder(Long orderId) {

        Order order = getOrder(orderId);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }

        order.cancel();
    }

    private Order getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 주문입니다."));

        return order;
    }


}
