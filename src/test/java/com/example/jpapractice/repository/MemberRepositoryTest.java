package com.example.jpapractice.repository;

import com.example.jpapractice.config.QuerydslConfig;
import com.example.jpapractice.domain.Member;
import com.example.jpapractice.dto.MemberSearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("이메일로 회원을 조회한다")
    void findByEmail() {
        Member member = Member.create("kim", "kim@test.com", 30);
        memberRepository.save(member);

        Member foundMember = memberRepository.findByEmail("kim@test.com").orElseThrow();

        assertThat(foundMember.getName()).isEqualTo("kim");
        assertThat(foundMember.getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("QueryDSL로 회원을 동적 검색한다")
    void search() {
        memberRepository.save(Member.create("kim", "kim@test.com", 30));
        memberRepository.save(Member.create("lee", "lee@test.com", 25));
        memberRepository.save(Member.create("park", "park@test.com", 40));

        MemberSearchCondition condition = new MemberSearchCondition("k", 20, 35);

        List<Member> result = memberRepository.search(condition);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("kim");
    }
}
