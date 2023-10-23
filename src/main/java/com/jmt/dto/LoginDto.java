package com.jmt.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginDto {
    private String userid;   // 자체 로그인시 사용할 ID
    private String email;    // 소셜 로그인시 email 사용
    private String password; // 비밀번호
    private String accessToken;    // access Token
    private String refreshToken;    // refresh Token
}
