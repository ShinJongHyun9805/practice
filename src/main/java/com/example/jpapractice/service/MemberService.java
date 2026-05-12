package com.example.jpapractice.service;

import com.example.jpapractice.dto.MemberUpdateRequest;
import com.example.jpapractice.repository.MemberRepository;
import com.example.jpapractice.dto.MemberCreateRequest;
import com.example.jpapractice.entity.Member;
import com.example.jpapractice.response.MemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional()
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = new Member(
                request.name(),
                request.email(),
                request.age()
        );

        return memberRepository.save(member).getId();
    }

    public MemberResponse findMember(Long memberId) {
        Member member = getMember(memberId);

        return MemberResponse.from(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequest request) {
        Member member = getMember(memberId);

        member.updateProfile(request.name(), request.age());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.delete(getMember(memberId));
    }


    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
    }
}
