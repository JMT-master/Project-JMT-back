package com.jmt.service;

import com.jmt.common.TokenProvidor;
import com.jmt.dto.IdFindDto;
import com.jmt.dto.LoginDto;
import com.jmt.dto.MemberDto;
import com.jmt.dto.PasswordFindDto;
import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvidor tokenProvidor;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public Member getMember(String userid){
        return memberRepository.findByUserid(userid);
    }

    // 회원가입 인증
    // check : false(회원가입), true(수정)
    private void validate(Member member, boolean check) {
        try {
            // 비어있을 때
            if(member == null || member.getUserid() == null ||
                    member.getUsername() == null || member.getPassword() == null || member.getPasswordChk() == null ||
                    member.getZipcode() == null || member.getAddress() == null || member.getAddressDetail() == null ||
                    member.getPhone() == null || member.getAdminYn() == null) {
                throw new RuntimeException("invalid argument");

            } else if(!member.getPassword().equals(member.getPasswordChk())) {
                throw new RuntimeException("비밀번호 다름");
            }else if(!check && memberRepository.existsById(member.getUserid())) {
                throw new RuntimeException("이미 등록된 사용자가 있습니다.");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("invalid argument");
        }


    }

    // 이메일 중복 확인
    @Transactional
    public Member emailValidate(MemberDto memberDto) {
        Member member;
        member = memberRepository.findById(memberDto.getUserid()).orElseThrow(EntityNotFoundException::new);

        return member;
    }

    // 회원 가입
    @Transactional
    public MemberDto create(MemberDto memberDto) {
        Member member = MemberDto.toEntity(memberDto);

        System.out.println("memberDto = " + memberDto);
        System.out.println("member = " + member);

        validate(member,false);
        System.out.println("다음???????");

        // password 암호화
        String encodePwd = passwordEncoder.encode(member.getPassword());
        String encodePwdChk = passwordEncoder.encode(member.getPasswordChk());

        member.setPassword(encodePwd);
        member.setPasswordChk(encodePwdChk);

        System.out.println("-------------------------------------");
        return MemberDto.toDto(memberRepository.save(member));
    }
    
    // 회원 수정
    @Transactional
    public String update(MemberDto memberDto) {
        Member member = MemberDto.toEntity(memberDto);

        validate(member, true);

        // Dirty Checking(변경감지)로 인하여 update문이 따로 필요 없이 준속성에 의하여 조회 후 변경하면 자동 변경
        Optional<Member> id = memberRepository.findById(member.getUserid());
        Member result = id.orElseThrow(EntityNotFoundException::new);

        // password 암호화
        String encodePwd = passwordEncoder.encode(member.getPassword());
        String encodePwdChk = passwordEncoder.encode(member.getPasswordChk());

        member.setPassword(encodePwd);
        member.setPasswordChk(encodePwdChk);

        result.changeMember(member);

        return member.getUserid();
    }
    
    // 회원 탈퇴
    
    
    // 로그인
    @Transactional
    public LoginDto login(LoginDto loginDto) {
        Optional<Member> member = memberRepository.findById(loginDto.getUserid());

        // Id가 Repository에 있으면
        if(member.isPresent() && passwordEncoder.matches(loginDto.getPassword(), member.get().getPassword())) {
            Member resultMember = member.get();

            String accessToken = tokenProvidor.createAcessToken(resultMember.getUserid());
            String refreshToken = tokenProvidor.createRefreshToken(resultMember.getUserid());

            Cookie accessCookie = tokenProvidor.createCookie("ACCESS_TOKEN", accessToken);

            return LoginDto.builder()
                    .userid(resultMember.getUserid())
                    .accessToken(accessCookie)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new RuntimeException("등록되지 않은 사용자입니다.");
        }

    }
    
    // 로그 아웃

    // 아이디 찾기
    public String findUserId(IdFindDto idFindDto) {
        Member member = memberRepository.findByUsernameAndPhone(idFindDto.getUsername(), idFindDto.getPhone());
        if(member != null) {
            return member.getUserid();
        }
        return null;
    }

    // 비밀번호 찾기
    // 이메일 인증 보낸 후 맞으면 재확인 하게끔 설정
//    public String findPassWord(PasswordFindDto passwordFindDto) {
//        Member member = memberRepository.findByUseridAndEmail(passwordFindDto.getId(), passwordFindDto.getEmail());
//
//        if(member != null) {
//            return member.getPassword();
//        }
//
//        return null;
//    }

    // 메일 인증
    
    // 소셜 로그인
    // 카카오
    
    // 구글

    /*
    public void validationUser(String username){
        Member findMember = memberRepository.findByUsername(username);
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
    */
}
