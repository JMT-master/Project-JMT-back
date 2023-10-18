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
        System.out.println("dto = " + dto);
        service.validationUser(dto.getUsername());
        Member entity = dto.toEntity(dto);
        entity.setUserid(null);
        if(entity == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        MemberDto memberDto = MemberDto.toDto(entity);
        log.info("member = " + entity);
        try{
            service.regMember(entity);
            return ResponseEntity.ok().body(memberDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(memberDto);
        }

    }
}
