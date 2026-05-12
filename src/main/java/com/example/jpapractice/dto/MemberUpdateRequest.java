package com.example.jpapractice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Min(value = 1, message = "나이는 1 이상이어야 합니다.")
        int age
) {
}
