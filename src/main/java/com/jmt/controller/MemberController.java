package com.jmt.controller;

import com.jmt.dto.LoginDto;
import com.jmt.dto.MemberDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.Member;
import com.jmt.service.EmailService;
import com.jmt.service.KaKaoLoginService;
import com.jmt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.EntityResponse;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@Slf4j
public class MemberController {

    @Autowired
    MemberService service;

    @Autowired
    EmailService emailService;

    @Autowired
    KaKaoLoginService kaKaoLoginService;

    @PostMapping("joinUser")
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto dto){
        MemberDto memberDto = null;

        try{
            memberDto = service.create(dto);
            return ResponseEntity.ok().body(memberDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(memberDto);
        }

    }

    // 이메일 중복 확인
    @PostMapping("joinUser/email")
    public ResponseEntity<ResponseDto> sendEmailChk(@RequestBody MemberDto memberDto) {
        Member member = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            member = service.emailValidate(memberDto);
            responseDto.setError("false");
            return ResponseEntity.badRequest().body(responseDto);
        } catch (Exception e) {
            responseDto.setError("success");
            return ResponseEntity.ok().body(responseDto);
        }
    }

    // 메일 인증받기 클릭 시
    @PostMapping("joinUser/email/validateSend")
    public ResponseEntity<ResponseDto> sendEmailValidate(@RequestBody MemberDto memberDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            emailService.sendMail(memberDto.getUserid());
            responseDto.setError("success");
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setError("error");
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    // 메일 인증받기 인증확인 눌렀을 때
    @PostMapping("joinUser/email/validateCheck")
    public ResponseEntity<ResponseDto> validateEmail(@RequestBody MemberDto memberDto) {
        ResponseDto responseDto = new ResponseDto();

        responseDto.setError("success");
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("login")
    public ResponseEntity<LoginDto> loginMember(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        LoginDto login = null;
        try{
            login = service.login(loginDto);
            response.addCookie(login.getAccessToken());
            System.out.println("login = " + login);
            return ResponseEntity.ok().body(login);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 카카오 로그인
    @GetMapping("login/auth")
    public String getAccessToken(@RequestParam("code") String code) {
        String kaKaoToken = kaKaoLoginService.getKaKaoToken(code);
        String kaKaoTokenInfo = kaKaoLoginService.getKaKaoTokenInfo(kaKaoToken);

        return null;
    }
}
