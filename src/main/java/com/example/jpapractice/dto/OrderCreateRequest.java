package com.example.jpapractice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(

        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,

        @NotBlank(message = "주문번호는 필수입니다.")
        String orderNumber
) {
}
