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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvidor tokenProvidor;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 멤버 정보 호출
    @Transactional
    public Member getMember(String email,String socialYn){
        return memberRepository.findByEmailAndSocialYn(email,socialYn).get();
    }

    // 회원가입 인증
    // check : false(회원가입), true(수정)
    private void validate(Member member, boolean check) {
        List<String> memberList = new ArrayList<>();
        memberList.add(member.getEmail());

        try {
            // 회원 수정
            if(check) {
                // 비어있을 때
                if(member.getEmail().isEmpty() ||
                        member.getUsername().isEmpty() ||
                        member.getZipcode().isEmpty() || member.getAddress().isEmpty() ||
                        member.getPhone().isEmpty() || member.getSocialYn().isEmpty() || member.getAdminYn().isEmpty()) {
                    throw new RuntimeException("비어있는 칸이 있습니다.");

                } else if(!memberRepository.findByPhoneAndEmailNotIn(member.getPhone(),memberList).isEmpty()) {
                    throw new RuntimeException("이미 등록된 전화번호");
                }
            } else {
                // 회원 가입
                // 비어있을 때
                if(member.getEmail().isEmpty() ||
                        member.getUsername().isEmpty() || member.getPassword().isEmpty() || member.getPasswordChk().isEmpty() ||
                        member.getZipcode().isEmpty() || member.getAddress().isEmpty() ||
                        member.getPhone().isEmpty() || member.getSocialYn().isEmpty() || member.getAdminYn().isEmpty()) {
                    throw new RuntimeException("비어있는 칸이 있습니다.");

                } else if(!member.getPassword().equals(member.getPasswordChk())) {
                    throw new RuntimeException("비밀번호 다름");
                } else if(!check && memberRepository.existsByEmailAndSocialYn(member.getEmail(),member.getSocialYn())) {
                    throw new RuntimeException("이미 등록된 사용자가 있습니다.");
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    // 이메일 중복 확인
    @Transactional
    public Member emailValidate(MemberDto memberDto) {
        Member member = null;
        member = memberRepository.findByEmailAndSocialYn(memberDto.getEmail(), memberDto.getSocialYn()).orElseThrow(EntityNotFoundException::new);

        return member;
    }

    // 회원 가입
    @Transactional
    public MemberDto create(MemberDto memberDto) {
        Member member = MemberDto.toEntity(memberDto);

        System.out.println("memberDto = " + memberDto);
        System.out.println("member = " + member);

        try{
            validate(member,false);
            System.out.println("다음???????");

            // password 암호화
            String encodePwd = passwordEncoder.encode(member.getPassword());
            String encodePwdChk = passwordEncoder.encode(member.getPasswordChk());
            System.out.println("다음22222");
            member.setPassword(encodePwd);
            member.setPasswordChk(encodePwdChk);

            System.out.println("-------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return MemberDto.toDto(memberRepository.save(member));
    }
    
    // 회원 수정
    @Transactional
    public String update(MemberDto memberDto) {
        Member result = null;
        Member member = MemberDto.toEntity(memberDto);

        try {
            validate(member, true);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }


        // Dirty Checking(변경감지)로 인하여 update문이 따로 필요 없이 준속성에 의하여 조회 후 변경하면 자동 변경
        Optional<Member> optionalMember = memberRepository.findByEmailAndSocialYn(member.getEmail(),member.getSocialYn());

        if(optionalMember.isEmpty()) {
            return null;
        } else {
            Member revMember = optionalMember.get();
            result = Member.builder()
                    .userid(revMember.getUserid())
                    .email(revMember.getEmail())
                    .password(revMember.getPassword())
                    .passwordChk(revMember.getPasswordChk())
                    .adminYn(revMember.getAdminYn())
                    .socialYn(revMember.getSocialYn())

                    .username(member.getUsername())
                    .zipcode(member.getZipcode())
                    .address(member.getAddress())
                    .addressDetail(member.getAddressDetail())
                    .phone(member.getPhone())
                    .build();
        }

        memberRepository.save(result);

        return member.getEmail();
    }
    
    // 회원 탈퇴
    
    
    // 로그인
    @Transactional
    public LoginDto login(LoginDto loginDto) {
        Optional<Member> member = memberRepository.findByEmailAndSocialYn(loginDto.getEmail(),loginDto.getSocialYn());

        // Id가 Repository에 있으면
        if(member.isPresent() &&
                ((member.get().getSocialYn().equals("N") &&
                passwordEncoder.matches(loginDto.getPassword(), member.get().getPassword())) ||
        member.get().getSocialYn().equals("Y"))) {
            Member resultMember = member.get();

            String accessToken = tokenProvidor.createAcessToken(resultMember.getEmail());
            String refreshToken = tokenProvidor.createRefreshToken(resultMember.getEmail());

            Cookie accessCookie = tokenProvidor.createCookie("ACCESS_TOKEN", accessToken);

            Cookie adminChk = tokenProvidor.createCookie("adminChk", member.get().getAdminYn());

            return LoginDto.builder()
                    .userid(resultMember.getEmail())
                    .accessToken(accessCookie)
                    .refreshToken(refreshToken)
                    .adminChk(adminChk)
                    .build();
        } else {
            return null;
        }

    }

    // 로그인 정보 제공
    @Transactional
    public LocalDateTime loginInfo(LoginDto loginDto) {
        Optional<Member> member = memberRepository.findByEmailAndSocialYn(loginDto.getEmail(),loginDto.getSocialYn());

        if(member.isPresent()) {
            return LocalDateTime.now();
        } else {
            return null;
        }
    }

    // 로그인 시간 연장
    @Transactional
    public LoginDto loginExtension(LoginDto loginDto) {
        Optional<Member> member = memberRepository.findByEmailAndSocialYn(loginDto.getEmail(),loginDto.getSocialYn());

        // Id가 Repository에 있으면
        if(member.isPresent()) {
            Member resultMember = member.get();

            String accessToken = tokenProvidor.createAcessToken(resultMember.getEmail());
            String refreshToken = tokenProvidor.createRefreshToken(resultMember.getEmail());

            System.out.println("accessToken = " + accessToken);

            Cookie accessCookie = tokenProvidor.createCookie("EXTENSION_TOKEN", accessToken);

            return LoginDto.builder()
                    .userid(resultMember.getEmail())
                    .accessToken(accessCookie)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            System.out.println("여기??");
            return null;
        }

    }
    
    // 로그 아웃

    // 아이디 찾기
    public String findUserId(IdFindDto idFindDto) {
        System.out.println("idFindDto = " + idFindDto);
        Member member = memberRepository.findByUsernameAndPhone(idFindDto.getUsername(), idFindDto.getPhone());
        System.out.println("member = " + member);
        if(member != null) {
            return member.getEmail();
        }
        return "없음";
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
    public Member kakaoMember(MemberDto memberDto) {
        Optional<Member> member = memberRepository.findByEmailAndSocialYn(memberDto.getEmail(), memberDto.getSocialYn());

        System.out.println("member = " + member.isPresent());
        if(member.isPresent()) {
            Member getMember = member.get();
            getMember.setSocialToken(memberDto.getSocialToken());
            Member updateMember = memberRepository.save(getMember);
            return updateMember;
        } else {
            Member entityMember = MemberDto.toEntity(memberDto);
            Member saveMember = memberRepository.save(entityMember);
            return saveMember;
        }
    }
    
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
