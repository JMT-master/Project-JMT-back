package com.jmt.service;

public interface SocialLoginService {

    //소셜 로그인 제공하는 사이트 정보
    String getProvider();
    //소셜 로그인 이메일 정보
    String getEmail();
    //소셜 로그인의 닉네임 정보
    String getNickName();


}
