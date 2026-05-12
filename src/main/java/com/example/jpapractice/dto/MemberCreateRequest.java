package com.example.jpapractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @Min(value = 1, message = "나이는 1 이상이어야 합니다.")
        int age
) {
}
