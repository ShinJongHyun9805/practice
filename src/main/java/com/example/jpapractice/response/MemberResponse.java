package com.example.jpapractice.response;

import com.example.jpapractice.entity.Member;

public record MemberResponse(
        Long id,
        String name,
        String email,
        int age
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAge()
        );
    }
}