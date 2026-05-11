package com.example.jpapractice;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse join(@Valid MemberCreateRequest request) {

        // 이메일 중복 + 중복시 예외
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }

        // Member 생성
        Member member = new Member();
        member.setAge(30);
        member.setName("테스터");
        member.setEmail("test@test.com");

        // Repository 저장
        Member save = memberRepository.save(member);

        // Member Id 반환
        return new MemberResponse().responseSave(save.getId());
    }
}
