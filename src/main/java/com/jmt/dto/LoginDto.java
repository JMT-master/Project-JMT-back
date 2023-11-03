package com.jmt.dto;

import lombok.*;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String userid;   // 자체 로그인시 사용할 ID
    private String email;    // 소셜 로그인시 email 사용
    private String password; // 비밀번호
    private Cookie accessToken;    // access Token
    private String refreshToken;    // refresh Token
    private Cookie adminChk;
    private LocalDateTime loginTime; // login 시간 확인
    private Boolean loginState;      // 로그인 상태 유지
    private String socialYn;        // 소셜 로그인 'Y', 일반 로그인(회원가입) 'N'
}
