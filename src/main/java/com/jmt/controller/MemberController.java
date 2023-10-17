package com.jmt.controller;

import com.jmt.dto.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @PostMapping("/joinUser")
    public ResponseEntity<?> createUser(MemberDto memberDto) {

    }
}
