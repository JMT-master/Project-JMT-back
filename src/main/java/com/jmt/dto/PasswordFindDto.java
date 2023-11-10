package com.jmt.dto;

import lombok.Data;

@Data
public class PasswordFindDto {
    private String preId;
    private String prePwd;
    private String newPwd;
    private String newPwdChk;
    private String social;
}
