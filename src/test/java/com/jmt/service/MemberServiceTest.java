package com.jmt.service;

import com.jmt.dto.MemberDto;
import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository repository;

    @Test
    void memberCreate() {
        MemberDto memberDto = new MemberDto(
                "test","홍길동","1234","1234",
                "011111","서울시","양천구","010-2345-7891","test@naver.com","Y"
        );

        MemberDto result = memberService.create(memberDto);

        System.out.println("result = " + result);
        assertEquals(memberDto, result);

    }

    @Test
    @DisplayName("강제 에러 Test")
    void memberCreateError() {
        MemberDto memberDto = new MemberDto();

        MemberDto test = memberDto.builder()
                .userid("test")
                .username("홍길동")
                .password("1234")
                .passwordChk("1234")
                .zipcode("011111")
                .address("서울시")
                .addressDetail("양천구")
                .phone("010-2345-7891")
                .email("test@naver.com")
                .adminYn("N")
                .build();

        MemberDto result = memberService.create(test);

        System.out.println("result = " + result);
        assertEquals(test, result);
    }

    @Test
    void update() {
        MemberDto memberDto = new MemberDto(
                "test","홍길동","1234","1234",
                "011111","서울시","양천구","010-2345-7891","test@naver.com","Y"
        );

        MemberDto result = memberService.create(memberDto);

        System.out.println("create 후 result = " + result);

        MemberDto memberDto2 = new MemberDto(
                "test","홍길동2","1234","1234",
                "11111","서울시zzz","양fff천구","010-2345-7891","test@naver.com","Y"
        );

        String updateId = memberService.update(memberDto2);

        Member member = repository.findById(updateId).get();
        Member compareMember = MemberDto.toEntity(memberDto2);

        System.out.println("updateId = " + updateId);

        assertEquals(member, compareMember);
    }

}