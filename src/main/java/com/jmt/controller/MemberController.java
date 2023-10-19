package com.jmt.controller;

import com.jmt.dto.MemberDto;
import com.jmt.entity.Member;
import com.jmt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/")
@Slf4j
public class MemberController {

    @Autowired
    MemberService service;
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

    @PostMapping("login")
    public ResponseEntity<LoginDto> loginMember(@RequestBody LoginDto loginDto) {
        System.out.println("Login 입장");
        System.out.println("loginDto = " + loginDto);
        LoginDto login = null;
        try{
            login = service.login(loginDto);
            return ResponseEntity.ok().body(login);
        } catch (Exception e) {
            System.out.println("????????");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
