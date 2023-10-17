package com.jmt.service;

import com.jmt.dto.MemberDto;
import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository repository;

    // 회원가입 인증
    // check : false(회원가입), true(수정)
    private void validate(Member member, boolean check) {
        // 비어있을 때
        if(member == null || member.getUserid() == null ||
           member.getUsername() == null || member.getPassword() == null || member.getPasswordChk() == null ||
        member.getZipcode() == null || member.getAddress() == null || member.getAddressDetail() == null ||
        member.getPhone() == null || member.getAdminYn() == null) {
            throw new RuntimeException("invalid argument");

        } else if(!member.getPassword().equals(member.getPasswordChk())) {
            throw new RuntimeException("비밀번호 다름");
        }else if(!check && repository.existsById(member.getUserid())) {
            throw new RuntimeException("이미 등록된 사용자가 있습니다.");
        }
    }

    // 회원 가입
    @Transactional
    public MemberDto create(MemberDto memberDto) {

        Member member = MemberDto.toEntity(memberDto);

        validate(member,false);
        System.out.println("create");
        return MemberDto.toDto(repository.save(member));
    }
    
    // 회원 수정
    @Transactional
    public String update(MemberDto memberDto) {
        Member member = MemberDto.toEntity(memberDto);

        validate(member, true);

        // Dirty Checking(변경감지)로 인하여 update문이 따로 필요 없이 준속성 영
        Optional<Member> id = repository.findById(member.getUserid());
        Member result = id.orElseThrow(EntityNotFoundException::new);

        result.setUserid(member.getUserid());
        result.setUsername(member.getUsername());
        result.setPassword(member.getPassword());
        result.setPasswordChk(member.getPasswordChk());
        result.setZipcode(member.getZipcode());
        result.setAddress(member.getAddress());
        result.setAddressDetail(member.getAddressDetail());
        result.setPhone(member.getPhone());
        result.setEmail(member.getEmail());
        result.setAdminYn(member.getAdminYn());

        System.out.println("result = " + result);

        return member.getUserid();
    }
    
    
    // 회원 탈퇴
    
    
    // 로그인
    
    
    // 로그 아웃

    @Transactional
    public Member getMember(String username){
        return repository.findByUsername(username);
    }

    /*
    public void validationUser(String username){
        Member findMember = repository.findByUsername(username);
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
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
    */
}
