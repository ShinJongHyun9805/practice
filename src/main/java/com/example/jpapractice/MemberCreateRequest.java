package com.example.jpapractice;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberCreateRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Min(1)
    private int age;
}
