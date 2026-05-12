package com.example.jpapractice.service;

import com.example.jpapractice.dto.MemberCreateRequest;
import com.example.jpapractice.dto.MemberUpdateRequest;
import com.example.jpapractice.entity.Member;
import com.example.jpapractice.repository.MemberRepository;
import com.example.jpapractice.response.MemberResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 성공")
    void joinSuccess() {

        MemberCreateRequest request = new MemberCreateRequest("tester", "test@test.com", 30);

        Long memberId = memberService.join(request);

        MemberResponse member = memberService.findMember(memberId);

        assertTrue(member != null);
        assertEquals(member.name(), request.name());
        assertEquals(member.email(), request.email());
        assertEquals(member.age(), request.age());

    }

    @Test
    @DisplayName("이미 존재하는 이메일은 회원 가입 불가")
    void existsEmail() {

        MemberCreateRequest request1 = new MemberCreateRequest("tester", "test@test.com", 30);

        MemberCreateRequest request2 = new MemberCreateRequest("testrr", "test@test.com", 31);

        memberService.join(request1);

        assertThatThrownBy(() -> memberService.join(request2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 이메일입니다.");
    }

    @Test
    @DisplayName("회원 단건 조회 성공")
    void findMember() {

        MemberCreateRequest request = new MemberCreateRequest("tester", "test@test.com", 30);

        Long memberId = memberService.join(request);

        MemberResponse member = memberService.findMember(memberId);

        assertTrue(member != null);
    }

    @Test
    @DisplayName("회원 정보를 수정할 수 있다")
    void updateMember() {
        Long memberId = memberService.join(
                new MemberCreateRequest("kim", "kim@test.com", 30)
        );

        memberService.updateMember(
                memberId,
                new MemberUpdateRequest("lee", 31)
        );

        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        assertThat(member.getName()).isEqualTo("lee");
        assertThat(member.getAge()).isEqualTo(31);
    }

    @Test
    @DisplayName("회원을 삭제할 수 있다")
    void deleteMember() {
        Long memberId = memberService.join(
                new MemberCreateRequest("kim", "kim@test.com", 30)
        );

        memberService.deleteMember(memberId);

        assertThat(memberRepository.findById(memberId)).isEmpty();
    }

}