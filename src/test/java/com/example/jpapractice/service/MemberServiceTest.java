package com.example.jpapractice.service;

import com.example.jpapractice.domain.Member;
import com.example.jpapractice.dto.MemberCreateRequest;
import com.example.jpapractice.dto.MemberResponse;
import com.example.jpapractice.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("중복 이메일이 아니면 회원을 생성한다")
    void create() {
        MemberCreateRequest request = new MemberCreateRequest("kim", "kim@test.com", 30);
        Member savedMember = Member.create("kim", "kim@test.com", 30);

        when(memberRepository.existsByEmail("kim@test.com")).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        MemberResponse response = memberService.create(request);

        assertThat(response.name()).isEqualTo("kim");
        assertThat(response.email()).isEqualTo("kim@test.com");
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("중복 이메일이면 예외가 발생한다")
    void createDuplicateEmail() {
        MemberCreateRequest request = new MemberCreateRequest("kim", "kim@test.com", 30);

        when(memberRepository.existsByEmail("kim@test.com")).thenReturn(true);

        assertThatThrownBy(() -> memberService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 이메일입니다.");
    }
}
