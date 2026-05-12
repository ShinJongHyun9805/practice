package com.example.jpapractice.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("이름이 비어 있으면 회원을 생성할 수 없다")
    void createFailByBlankName() {
        assertThatThrownBy(() -> new Member("", "kim@test.com", 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어 있을 수 없습니다.");
    }

    @Test
    void createFailByEmail() {
        assertThatThrownBy(() -> new Member("test", "", 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 비어 있을 수 없습니다.");
    }

    @Test
    void createFailByInvalidAge() {
        assertThatThrownBy(() -> new Member("test", "email@email.com", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("나이는 1 이상이어야 합니다.");
    }

}