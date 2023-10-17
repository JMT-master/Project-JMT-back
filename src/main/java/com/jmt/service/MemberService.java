package com.jmt.service;

import com.jmt.dto.MemberDto;
import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository repository;

    @Transactional
    public Member getMember(String username){
        return repository.findByUsername(username);
    }

    @Transactional
    public void regMember(Member member){
        try {
            log.info("====notice : " + member);
            repository.save(member);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
