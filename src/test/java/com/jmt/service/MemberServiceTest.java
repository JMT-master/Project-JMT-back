//package com.jmt.service;
//
//import com.jmt.dto.IdFindDto;
//import com.jmt.dto.MemberDto;
//import com.jmt.dto.PasswordFindDto;
//import com.jmt.entity.Member;
//import com.jmt.repository.MemberRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    void memberCreate() {
//        MemberDto memberDto = new MemberDto(
//                "test","홍길동","abc@abc.com","1234","1234",
//                "011111","서울시","양천구","010-2345-7891","Y","N"
//        );
//
//        MemberDto result = memberService.create(memberDto);
//
//        Member repositoryUser = memberRepository.findById(memberDto.getUserid()).get();
//        MemberDto repositoryUserDto = MemberDto.toDto(repositoryUser);
//
//        System.out.println("result = " + result);
//
//        assertEquals(result, repositoryUserDto);
//
//    }
//
//    /*
//    @Test
//    @DisplayName("강제 에러 Test")
//    void memberCreateError() {
//        MemberDto memberDto = new MemberDto();
//
//        MemberDto test = memberDto.builder()
//                .userid("test")
//                .username("홍길동")
//                .password("1234")
//                .passwordChk("1234")
//                .zipcode("011111")
//                .address("서울시")
//                .addressDetail("양천구")
//                .phone("010-2345-7891")
//                .email("test@naver.com")
//                .adminYn("N")
//                .build();
//
//        MemberDto result = memberService.create(test);
//
//        System.out.println("result = " + result);
//        assertEquals(test, result);
//    }
//    */
//
//    @Test
//    void update() {
//        MemberDto memberDto = new MemberDto(
//                "test","홍길동","abc@abc.com","1234","1234",
//                "011111","서울시","양천구","010-2345-7891","Y","N"
//        );
//
//        MemberDto result = memberService.create(memberDto);
//
//        System.out.println("create 후 result = " + result);
//
//        MemberDto memberDto2 = new MemberDto(
//                "test","홍길동2","abc@abc.com","1234","1234",
//                "11111","서울시zzz","양fff천구","010-2345-7891","Y","N"
//        );
//
//        String updateId = memberService.update(memberDto2);
//
//        Member member = memberRepository.findById(updateId).get();
//
//        System.out.println("member = " + member);
//
//        System.out.println("updateId = " + updateId);
//
//        assertNotEquals(result, member);
//    }
//
//    @Test
//    void memberFindIdTest() {
//        MemberDto memberDto = new MemberDto(
//                "test","홍길동","abc@abc.com","1234","1234",
//                "011111","서울시","양천구","010-2345-7891","Y","N"
//        );
//
//        MemberDto result = memberService.create(memberDto);
//
//        IdFindDto idFindDto = new IdFindDto();
//        idFindDto.setUsername("홍길동");
//        idFindDto.setPhone("010-2345-7891");
//
//        String user = memberService.findUserId(idFindDto);
//        assertEquals(user, "test");
//
//    }
//
////    @Test
////    void memberFindPasswordTest() {
////        MemberDto memberDto = new MemberDto(
////                "test","홍길동","1234","1234",
////                "011111","서울시","양천구","010-2345-7891","Y"
////        );
////
////        MemberDto result = memberService.create(memberDto);
////
////        PasswordFindDto passwordFindDto = new PasswordFindDto();
////        passwordFindDto.setId(memberDto.getUserid());
////
////        String pwd = memberService.findPassWord(passwordFindDto);
////        assertEquals(pwd, "1234");
////
////    }
//
//
//}