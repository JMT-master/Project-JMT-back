package com.jmt.service;

import com.jmt.dto.MemberDto;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private void validate(final MemberDto memberDto) {
        if(memberDto == null) {
            throw new RuntimeException("Entity is null");
        }

        if(memberDto.getUserid() == null) {
            throw new RuntimeException("Unknown user.");
        }
    }

    public void createMember(MemberDto memberDto) {
        validate(memberDto);



        memberRepository.save()
    }
}
