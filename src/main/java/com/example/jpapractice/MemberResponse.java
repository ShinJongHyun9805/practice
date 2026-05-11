package com.example.jpapractice;

public class MemberResponse {

    private Long id;

    protected MemberResponse() {}

    public MemberResponse(Long id) {
        this.id = id;
    }

    public MemberResponse responseSave(Long id) {
        return new MemberResponse(id);
    }
}
