package com.example.jpapractice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderItem(Order order, Product product, int quantity) {
        if (order == null) {
            throw new IllegalArgumentException("주문은 필수입니다.");
        }

        if (product == null) {
            throw new IllegalArgumentException("상품은 필수입니다.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }

        product.decreaseStock(quantity);

        this.order = order;
        this.product = product;
        this.orderPrice = product.getPrice();
        this.quantity = quantity;
    }

    public void cancel() {
        product.increaseStock(quantity);
    }

    public int getTotalPrice() {
        return orderPrice * quantity;
    }




}
