package com.jmt.service;

import java.util.HashMap;
import java.util.Map;

public class KakaoLoginService implements SocialLoginService{

    private Map<String, Object> kakaoAttributes;

    //카카오 attr에 정보를 저장한다.
    public KakaoLoginService(Map<String, Object> kakaoAttributes) {
        this.kakaoAttributes = kakaoAttributes;
    }

    //사이트명 가져오기
    @Override
    public String getProvider() {
        return "kakao";
    }

    //이메일 가져오기
    @Override
    public String getEmail() {
        HashMap<String, Object> account = (HashMap<String, Object>) kakaoAttributes.get("kakao_account");
        return (String) account.get("email");
    }

    @Override
    public String getNickName() {
        HashMap<String, Object> account = (HashMap<String, Object>) kakaoAttributes.get("properties");
        return (String) account.get("nickname");
    }
}
