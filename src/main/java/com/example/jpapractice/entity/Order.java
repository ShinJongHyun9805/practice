package com.example.jpapractice.entity;

import com.example.jpapractice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Order(String orderNumber, Member member) {
        validateOrderNumber(orderNumber);

        if (member == null) {
            throw new IllegalArgumentException("회원 정보는 필수입니다.");
        }

        this.orderNumber = orderNumber;
        this.member = member;
        this.orderStatus = OrderStatus.CREATED;

    }

    public void cancel() {
        if (this.orderStatus == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        this.orderStatus = OrderStatus.CANCELED;
    }

    private void validateOrderNumber(String orderNumber) {
        if (orderNumber == null || orderNumber.isBlank()) {
            throw new IllegalArgumentException("주문번호는 필수입니다.");
        }
    }
}
