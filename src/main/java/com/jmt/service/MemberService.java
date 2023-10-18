package com.jmt.service;

import com.jmt.dto.LoginDto;
import com.jmt.dto.MemberDto;
import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public Member getMember(String username){
        return memberRepository.findByUserid(username);
    }

//    // 회원가입 인증
//    // check : false(회원가입), true(수정)
//    private void validate(Member member, boolean check) {
//        // 비어있을 때
//        if(member == null || member.getUserid() == null ||
//           member.getUsername() == null || member.getPassword() == null || member.getPasswordChk() == null ||
//        member.getZipcode() == null || member.getAddress() == null || member.getAddressDetail() == null ||
//        member.getPhone() == null || member.getAdminYn() == null) {
//            throw new RuntimeException("invalid argument");
//
//        } else if(!member.getPassword().equals(member.getPasswordChk())) {
//            throw new RuntimeException("비밀번호 다름");
//        }else if(!check && memberRepository.existsById(member.getUserid())) {
//            throw new RuntimeException("이미 등록된 사용자가 있습니다.");
//        }
//    }
//
//    // 회원 가입
//    @Transactional
//    public MemberDto create(MemberDto memberDto) {
//        Member member = MemberDto.toEntity(memberDto);
//
//        validate(member,false);
//
//        // password 암호화
//        String encodePwd = passwordEncoder.encode(member.getPassword());
//        String encodePwdChk = passwordEncoder.encode(member.getPasswordChk());
//
//        member.setPassword(encodePwd);
//        member.setPasswordChk(encodePwdChk);
//
//        System.out.println("create");
//        return MemberDto.toDto(memberRepository.save(member));
//    }
//
//    // 회원 수정
//    @Transactional
//    public String update(MemberDto memberDto) {
//        Member member = MemberDto.toEntity(memberDto);
//
//        validate(member, true);
//
//        // Dirty Checking(변경감지)로 인하여 update문이 따로 필요 없이 준속성에 의하여 조회 후 변경하면 자동 변경
//        Optional<Member> id = memberRepository.findById(member.getUserid());
//        Member result = id.orElseThrow(EntityNotFoundException::new);
//
//        // password 암호화
//        String encodePwd = passwordEncoder.encode(member.getPassword());
//        String encodePwdChk = passwordEncoder.encode(member.getPasswordChk());
//
//        member.setPassword(encodePwd);
//        member.setPasswordChk(encodePwdChk);
//
//        System.out.println("member = " + member);
//
//        result.changeMember(member);
//
//        return member.getUserid();
//    }
//
//    // 회원 탈퇴
//
//
//    // 로그인
//    @Transactional
//    public LoginDto Login(LoginDto loginDto) {
//        Optional<Member> member = memberRepository.findById(loginDto.getUserid());
//
//        // Id가 Repository에 있으면
//        if(member.isPresent() && passwordEncoder.matches(loginDto.getPassword(), member.get().getPassword())) {
//            // token Create
//
//            // LoginDto에 token 추가
//
//            // return LoginDto
//            return null;
//        } else {
//            return null;
//        }
//
//    }
    
    
    // 로그 아웃

    // 아이디 찾기

    // 비밀번호 찾기

    // 메일 인증
    
    // 소셜 로그인
    // 카카오
    
    // 구글


    public void validationUser(String username){
        Member findMember = memberRepository.findByUserid(username);
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Transactional
    public void regMember(Member entity){
        try {
            log.info("====notice : " + entity);
            memberRepository.save(entity);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
