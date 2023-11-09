package com.jmt.controller;

import com.jmt.common.CaptchaUtil;
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
import nl.captcha.Captcha;
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
    public ResponseEntity<ResponseDto> createMember(@RequestBody MemberDto dto){
        MemberDto memberDto = null;
        List<String> msg = new ArrayList<>();

        try{
            memberDto = service.create(dto);
            return ResponseEntity.ok().body(ResponseDto.<String>builder()
                            .error("success")
                            .data(msg)
                    .build());
        }catch (RuntimeException e){
            System.out.println("여기?????");
            if(e.getMessage().equals("비어있는 칸이 있습니다.") ||
                    e.getMessage().equals("비밀번호 다름") ||
                    e.getMessage().equals("이미 등록된 사용자가 있습니다.")
            )
                msg.add(e.getMessage());
            else
                msg.add("서버 에러");
            return ResponseEntity.badRequest().body(ResponseDto.<String>builder()
                            .error("error")
                            .data(msg)
                    .build());
        } catch (Exception e) {
            System.out.println("서버에러");
            msg.add("서버 에러");
            return ResponseEntity.badRequest().body(ResponseDto.<String>builder()
                    .error("error")
                    .data(msg)
                    .build());
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
            return ResponseEntity.ok().body(responseDto);
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
    public ResponseEntity<ResponseDto> loginMember(@AuthenticationPrincipal String userid, @RequestParam String socialYn) {

        List<String> result = new ArrayList<>();
        Member member = service.getMember(userid,socialYn);
        if(member == null) {
            return ResponseEntity.ok().body(ResponseDto.<String>builder()
                    .error("error")
                    .data(result)
                    .build());
        } else {
            result.add(member.getEmail());

            return ResponseEntity.ok().body(ResponseDto.<String>builder()
                    .error("success")
                    .data(result)
                    .build());
        }
    }

    // 로그인 접속 시간
    @PostMapping("login/info")
    public ResponseEntity<LocalDateTime> loginStartTime(@RequestBody LoginDto loginDto) {
        List<LoginDto> loginDtos = new ArrayList<>();
        LocalDateTime dateTime = service.loginInfo(loginDto);
        System.out.println("dateTime = " + dateTime);
        System.out.println("들어옴???");

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
    public ResponseEntity<LocalDateTime> loginExtension(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try{

            System.out.println("userid = " + loginDto);
            LoginDto login = service.loginExtension(loginDto);
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

    // 카카오 member 가입
    @GetMapping("login/auth")
    public ResponseEntity<LoginDto> kakaoLogin(@RequestParam("code") String code) {
        String kaKaoToken = kaKaoLoginService.getKaKaoToken(code);
        String kaKaoTokenInfo = kaKaoLoginService.getKaKaoTokenInfo(kaKaoToken); // User Email

        System.out.println("kaKaoTokenInfo = " + kaKaoTokenInfo);

        try {
            // member table에 저장 및 토큰 변경 작업
            MemberDto memberDto = MemberDto.builder()
                    .email(kaKaoTokenInfo)
                    .adminYn("N")
                    .socialYn("Y")
                    .socialToken(kaKaoToken)
                    .build();

            Member member = service.kakaoMember(memberDto);

            // login 하여 우리 페이지에 맞는 토큰 및 쿠키 발급
            LoginDto login = LoginDto.builder()
                    .email(member.getEmail())
                    .socialYn(member.getSocialYn())
                    .build();

            System.out.println("결과값");
            return ResponseEntity.ok().body(login);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 카카오 로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity<String> kakaoLogout(@AuthenticationPrincipal String userId) {

        System.out.println("들어옴???");
        System.out.println("userId = " + userId);
        String result = kaKaoLoginService.sendKaKaoLogout(userId);
        return ResponseEntity.ok().body("ok");
    }

    //회원 정보 수정 화면 호출
    @GetMapping("member/update")
    public ResponseEntity<?> updateMember(@AuthenticationPrincipal String userId, @RequestParam String socialYn){
        try {
            log.info("update userId : "+userId);
            Member member = service.getMember(userId,socialYn);
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
        try {
            String email =  service.update(memberDto);
            // 추후 확인
            Member member = service.getMember(email,memberDto.getSocialYn());
            MemberDto dto = MemberDto.toDto(member);
            return ResponseEntity.ok().body(ResponseDto.builder()
                    .error("success")
                    .build());
        } catch (Exception e){
            return ResponseEntity.ok().body(ResponseDto.builder()
                            .error(e.getMessage())
                    .build());
        }
    }

    // 추후 확인
    //특정 userId의 userDto 값 가져오기
    @GetMapping("mypage")
    public ResponseEntity<?> getMember(@AuthenticationPrincipal String userId){
        try {
            log.info("update userId : "+userId);
            Member member = service.getMember(userId,"N");
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
    public ResponseEntity<ResponseDto> sendEmailCode(@RequestBody PwdFindDto pwdFindDto){
        try{
            service.checkMember(pwdFindDto, "N");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                            .error(e.getMessage())
                    .build());
        }

        String newPwd = emailService.sendNewPwdMail(pwdFindDto.getEmail());
        MemberDto memberDto = service.changePwdByRandomPwd(newPwd, pwdFindDto.getEmail());
        return ResponseEntity.ok().body(ResponseDto.builder()
                .error("success")
                .build());
    }

    @PostMapping("myInfo/ChangePasswd")
    public ResponseEntity<?> changePwd(@RequestBody PasswordFindDto passwordFindDto){
        MemberDto memberDto = service.updatePwdByNewPwd(passwordFindDto);
        return ResponseEntity.ok().body(memberDto);
    }

    @PostMapping("mypage/validate")
    public ResponseEntity validateMember(@AuthenticationPrincipal String userid, @RequestBody LoginDto loginDto) {
        String result = "";
        List<String> data = new ArrayList<>();
        try{
            result = service.validateMember(userid, loginDto);
        } catch (Exception e) {
            data.add(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        if(result.equals("fail")) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }
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
