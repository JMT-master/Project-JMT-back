package com.jmt.controller;

import com.jmt.common.TokenProvidor;
import com.jmt.dto.LoginDto;
import com.jmt.dto.MemberDto;
import com.jmt.dto.ResponseDto;
import com.jmt.dto.*;
import com.jmt.entity.Member;
import com.jmt.service.EmailService;
import com.jmt.service.KaKaoLoginService;
import com.jmt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.EntityResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class MemberController {

    @Autowired
    MemberService service;

    @Autowired
    EmailService emailService;

    @Autowired
    KaKaoLoginService kaKaoLoginService;

    @Autowired
    TokenProvidor tokenProvidor;


    // 회원가입
    @PostMapping("joinUser")
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto dto){
        MemberDto memberDto = null;
        System.out.println("memberDto = " + dto);

        try{
            memberDto = service.create(dto);
            return ResponseEntity.ok().body(memberDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(memberDto);
        }

    }

    // 이메일 중복 확인
    @PostMapping("joinUser/email")
    public ResponseEntity<ResponseDto> sendEmailChk(@RequestBody MemberDto memberDto) {
        Member member = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            member = service.emailValidate(memberDto);
            responseDto.setError("false");
            return ResponseEntity.badRequest().body(responseDto);
        } catch (Exception e) {
            responseDto.setError("success");
            return ResponseEntity.ok().body(responseDto);
        }
    }

    // 메일 인증받기 클릭 시
    @PostMapping("joinUser/email/validateSend")
    public ResponseEntity<ResponseDto> sendEmailValidate(@RequestBody MemberDto memberDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            emailService.sendMail(memberDto.getEmail());
            responseDto.setError("success");
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setError("error");
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    // 메일 인증받기 인증확인 눌렀을 때
    @PostMapping("joinUser/email/validateCheck")
    public ResponseEntity<ResponseDto> validateEmail(@RequestBody MemberDto memberDto) {
        ResponseDto responseDto = new ResponseDto();

        responseDto.setError("success");
        return ResponseEntity.ok().body(responseDto);
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<LoginDto> loginMember(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        LoginDto login = null;
        try{
            login = service.login(loginDto);
            login.setLoginTime(LocalDateTime.now());
            response.addCookie(login.getAdminChk());
            System.out.println("login = " + login);
            response.addCookie(login.getAccessToken());

            System.out.println("login22222222 = " + login);
            return ResponseEntity.ok().body(login);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 로그인 유저 전달
    @GetMapping("login/info")
    public ResponseEntity<String> loginMember(@AuthenticationPrincipal String userid) {
        Member member = service.getMember(userid);

        return ResponseEntity.ok().body(member.getEmail());
    }

    // 로그인 접속 시간
    @PostMapping("login/info")
    public ResponseEntity<LocalDateTime> loginStartTime(@RequestBody LoginDto loginDto) {
        List<LoginDto> loginDtos = new ArrayList<>();
        LocalDateTime dateTime = service.loginInfo(loginDto);

        if(dateTime == null) {
            return ResponseEntity.badRequest().body(dateTime);
        } else {
            LoginDto login = LoginDto.builder()
                    .loginTime(dateTime)
                    .build();

            loginDtos.add(login);
            return ResponseEntity.ok().body(dateTime);
        }
    }

    // 로그인 시간 연장
    @PostMapping("login/extension")
    public ResponseEntity<LocalDateTime> loginExtension(@RequestBody String userid, HttpServletResponse response) {
        try{

            LoginDto login = service.loginExtension(userid);
            response.addCookie(login.getAccessToken());

            return ResponseEntity.ok().body(LocalDateTime.now());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 로그인 만료시간 확인
    @GetMapping("login/expired")
    public ResponseEntity<ResponseDto> loginExpired(HttpServletRequest request, @AuthenticationPrincipal String userid) {
        String token = tokenProvidor.parseJwt(request);
        try {
            Boolean tokenExpired = tokenProvidor.isTokenExpired(token);

            // 토큰 기간 만료
            if(tokenExpired) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.builder()
                        .error("error")
                        .build());
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.builder()
                .error("success")
                .build());
    }

    // 카카오 로그인
    @GetMapping("login/auth")
    public String getAccessToken(@RequestParam("code") String code) {
        String kaKaoToken = kaKaoLoginService.getKaKaoToken(code);
        String kaKaoTokenInfo = kaKaoLoginService.getKaKaoTokenInfo(kaKaoToken);

        return null;
    }

    //회원 정보 수정
    @GetMapping("member/update")
    public ResponseEntity<?> updateMember(@AuthenticationPrincipal String userId){
        try {
            log.info("update userId : "+userId);
            Member member = service.getMember(userId);
            MemberDto memberDto = MemberDto.toDto(member);
            memberDto.setPassword(null);
            memberDto.setPasswordChk(null);
            log.info("memberDto : {}", memberDto);
            return ResponseEntity.ok().body(memberDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<String> responseDto = ResponseDto.<String>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("member/update")
    public ResponseEntity<?> update(@AuthenticationPrincipal String userId,
                                    @RequestBody MemberDto memberDto){
        log.info("memberDro : {}", memberDto);
       String email =  service.update(memberDto);
       log.info("email : "+email);
       Member member = service.getMember(email);
       MemberDto dto = MemberDto.toDto(member);
        return ResponseEntity.ok().body(dto);
    }

    //특정 userId의 userDto 값 가져오기
    @GetMapping("mypage")
    public ResponseEntity<?> getMember(@AuthenticationPrincipal String userId){
        try {
            log.info("update userId : "+userId);
            Member member = service.getMember(userId);
            MemberDto memberDto = MemberDto.toDto(member);
            memberDto.setPassword(null);
            memberDto.setPasswordChk(null);
            log.info("memberDto : {}", memberDto);
            return ResponseEntity.ok().body(memberDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<String> responseDto = ResponseDto.<String>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("findUserId")
    public ResponseEntity<?> findUserId(@RequestBody IdFindDto idFindDto){
        String userId = service.findUserId(idFindDto);
        System.out.println("userId = " + userId);
        return ResponseEntity.ok().body(userId);
    }

    @PostMapping("sendEmailCode")
    public ResponseEntity<?> sendEmailCode(@RequestBody PwdFindDto pwdFindDto){
        String newPwd = emailService.sendNewPwdMail(pwdFindDto.getEmail());
        MemberDto memberDto = service.changePwdByRandomPwd(newPwd, pwdFindDto.getEmail());
        return ResponseEntity.ok().body(memberDto);
    }

    @PostMapping("myInfo/ChangePasswd")
    public ResponseEntity<?> changePwd(@RequestBody PasswordFindDto passwordFindDto){
        MemberDto memberDto = service.updatePwdByNewPwd(passwordFindDto);
        return ResponseEntity.ok().body(memberDto);
    }
    // 메일 인증받기 클릭 시
//    @PostMapping("joinUser/email/validateSend")
//    public ResponseEntity<ResponseDto> sendEmailValidate(@RequestBody MemberDto memberDto) {
//        ResponseDto responseDto = new ResponseDto();
//        try {
//            emailService.sendMail(memberDto.getEmail());
//            responseDto.setError("success");
//            return ResponseEntity.ok().body(responseDto);
//        } catch (Exception e) {
//            responseDto.setError("error");
//            return ResponseEntity.badRequest().body(responseDto);
//        }
//    }
//
//    // 메일 인증받기 인증확인 눌렀을 때
//    @PostMapping("joinUser/email/validateCheck")
//    public ResponseEntity<ResponseDto> validateEmail(@RequestBody MemberDto memberDto) {
//        ResponseDto responseDto = new ResponseDto();
//
//        responseDto.setError("success");
//        return ResponseEntity.ok().body(responseDto);
//    }
//    @PostMapping("checkUser")
//    public ResponseEntity<UserChkDto> checkUser(@AuthenticationPrincipal String userid, @RequestBody MemberDto dto) {
//        UserChkDto chkDto = new UserChkDto();
//        Member member = service.getMember(userid);
//        if(dto!=null) {
//            chkDto.setIsSameUser(dto.getUserid().equals(userid));
//        }else{
//            chkDto.setIsSameUser(false);
//        }
//        chkDto.setIsAdmin(member.getAdminYn().equalsIgnoreCase("y"));
//        log.debug("chk dto : " + chkDto);
//        return ResponseEntity.ok().body(chkDto);
//    }
}
