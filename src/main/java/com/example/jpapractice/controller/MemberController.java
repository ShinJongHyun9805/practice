package com.example.jpapractice.controller;

import com.example.jpapractice.dto.MemberCreateRequest;
import com.example.jpapractice.dto.MemberUpdateRequest;
import com.example.jpapractice.response.MemberResponse;
import com.example.jpapractice.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Long join(@RequestBody @Valid MemberCreateRequest request) {
        return memberService.join(request);
    }

    @GetMapping("/{memberId}")
    public MemberResponse findMember(@PathVariable("memberId") Long memberId) {
        return memberService.findMember(memberId);
    }

    @PatchMapping("/{memberId}")
    public void updateMember(@PathVariable("memberId") Long memberId, @RequestBody @Valid MemberUpdateRequest request) {
        memberService.updateMember(memberId, request);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
    }
}
