package com.example.likelion13th.service;

import com.example.likelion13th.domain.Member;
import com.example.likelion13th.enums.Role;
import com.example.likelion13th.Repository.MemberRepository;
import com.example.likelion13th.Service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp()
    {
        memberRepository.deleteAll();

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .age(10 + i) // 나이관련 build 추가
                    .email("user" + i + "@test.com")
                    .address("서울시 테스트동 " + i + "번지")
                    .phoneNumber("010-1234-56" + String.format("%02d", i))
                    .deposit(1000 * i)
                    .isAdmin(false)
                    .role(Role.BUYER)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    void testGetMembersByPage()
    {
        Page<Member> page = memberService.getMembersByPage(0, 10);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }

    @Test
    void testGetAdultMembersSortedByName()
    {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Member> page = memberService.getMembersAge20OrAbove(pageable);

        // 11~40까지 age가 20이상인 구간은 20~40으로 총 21명이 된다.
        assertThat(page.getTotalElements()).isEqualTo(21);
        assertThat(page.getContent()).hasSize(10);

        // 이름 오름차순 정렬 확인
        assertThat(page.getContent().get(0).getName()).isEqualTo("user10");
        assertThat(page.getContent().get(9).getName()).isEqualTo("user19");

        // 모든 멤버의 나이가 20 이상인지 확인
        assertThat(page.getContent()).allMatch(m -> m.getAge() >= 20);
    }

    @Test
    void testGetMembersByNamePrefix()
    {
        Pageable pageable = PageRequest.of(0,10);
        Page<Member> page = memberService.getMembersByNamePrefix("user1", pageable);

        // name 생성 규칙이 user[i] 형태이므로 user1을 prefix로 받으면 user1, user10 ~ user 19까지 총 11명이 됨
        assertThat(page.getTotalElements()).isEqualTo(11);
        assertThat(page.getContent()).hasSize(10);

        // 이름 오름차순 정렬 확인하기
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
        assertThat(page.getContent().get(4).getName()).isEqualTo("user13");
    }
}
