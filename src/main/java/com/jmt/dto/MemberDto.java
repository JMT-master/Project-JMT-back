package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    @NotBlank(message = "아이디는 필수 입니다.")
    private String userid;
    private String username;
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;
    private String passwordCheck;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String adminYn;
}
