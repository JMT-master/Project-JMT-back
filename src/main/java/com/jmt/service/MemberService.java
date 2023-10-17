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

    public void validationUser(String username){
        Member findMember = repository.findByUsername(username);
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Transactional
    public Member getMember(String username){
        return repository.findByUsername(username);
    }

    @Transactional
    public void regMember(Member entity){
        try {
            log.info("====notice : " + entity);
            repository.save(entity);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
