package com.example.jpapractice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private int age;

    public Member(String name, String email, int age) {
        validateName(name);
        validateEmail(email);
        validateAge(age);

        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeAge(int age) {
        validateAge(age);
        this.age = age;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }

        if (name.length() > 30) {
            throw new IllegalArgumentException("이름은 30자를 초과할 수 없습니다.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 비어 있을 수 없습니다.");
        }

        if (email.length() > 100) {
            throw new IllegalArgumentException("이메일은 100자를 초과할 수 없습니다.");
        }
    }

    private void validateAge(int age) {
        if (age < 1) {
            throw new IllegalArgumentException("나이는 1 이상이어야 합니다.");
        }
    }
}