package com.jmt.controller;

import com.jmt.dto.LoginDto;
import com.jmt.dto.MemberDto;
import com.jmt.entity.CustomUser;
import com.jmt.entity.Member;
import com.jmt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping(value = "login"
//            , produces= MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public ResponseEntity<LoginDto> loginMember(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve the username (which is the userId in your case)
            userId = authentication.getName();

            log.debug("current userId: {}", userId);
        }

//        System.out.println("username = " + username);
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
