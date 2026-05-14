package com.example.jpapractice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(

        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,

        @NotBlank(message = "주문번호는 필수입니다.")
        String orderNumber,

        @NotBlank(message = "상품 ID는 필수입니다.")
        Long productId,

        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        int quantity
) {
}
