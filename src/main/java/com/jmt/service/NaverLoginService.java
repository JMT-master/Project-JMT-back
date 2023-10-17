package com.jmt.service;

import java.util.Map;

public class NaverLoginService implements SocialLoginService{

    private Map<String, Object> naverAttributes;

    public NaverLoginService(Map<String, Object> naverAttributes){
        this.naverAttributes = naverAttributes;
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String, Object> map = (Map<String, Object>) naverAttributes.get("response");
        return (String) map.get("email");
    }

    @Override
    public String getNickName() {
        Map<String, Object> map = (Map<String, Object>) naverAttributes.get("response");
        return (String) map.get("nickname");
    }
}
