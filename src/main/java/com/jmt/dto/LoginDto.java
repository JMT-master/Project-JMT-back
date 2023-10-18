package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String userid;   // 자체 로그인시 사용할 ID
    private String email;    // 소셜 로그인시 email 사용
    private String password; // 비밀번호
    private String token;    // 토큰
}
