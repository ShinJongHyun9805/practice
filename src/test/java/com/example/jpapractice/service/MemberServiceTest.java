package com.example.jpapractice.service;

import com.example.jpapractice.entity.Member;
import com.example.jpapractice.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원을 저장하고 ID로 조회할 수 있다")
    void saveAndFindById() {
        Member member = new Member("kim", "kim@test.com", 30);
        Member savedMember = memberRepository.save(member);

        Optional<Member> result = memberRepository.findById(savedMember.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("kim");
        assertThat(result.get().getEmail()).isEqualTo("kim@test.com");
        assertThat(result.get().getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("이메일로 회원을 조회할 수 있다")
    void findByEmail() {
        Member member = new Member("kim", "kim@test.com", 30);
        memberRepository.save(member);

        Optional<Member> result = memberRepository.findByEmail("kim@test.com");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("이메일 존재 여부를 확인할 수 있다")
    void existsByEmail() {
        Member member = new Member("kim", "kim@test.com", 30);
        memberRepository.save(member);

        boolean exists = memberRepository.existsByEmail("kim@test.com");

        assertThat(exists).isTrue();
    }
}