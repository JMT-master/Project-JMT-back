package com.jmt.service;

import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KaKaoLoginService {
    private String restApiKey = "1921d336e78e0f12cb65133fb93aeab0";
    private String redirectUri = "http://localhost:3000/login/auth";

    private final MemberRepository memberRepository;

    // 인가코드 이용 accessToken 추출
    public String getKaKaoToken(String code) {
        String accessToken = "";
        String revToken = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        // Set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",restApiKey);
        params.add("redirect_uri", redirectUri);
        params.add("code",code);

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params,headers);

        try{
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(revToken,request,String.class);
            String body = stringResponseEntity.getBody();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(body);

            accessToken = jsonObject.get("access_token").toString();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    // accessToken 이용 User 정보 추출
    public String getKaKaoTokenInfo(String token) {
        String revInfo = "https://kapi.kakao.com/v2/user/me";
        String email = "";

        RestTemplate restTemplate = new RestTemplate();

        // Set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(headers);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(revInfo,entity,String.class);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(forEntity.getBody().toString());
            System.out.println("jsonObject = " + jsonObject);

            JSONObject value =  (JSONObject) jsonObject.get("kakao_account");
            email = value.get("email").toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return email;
    }

    // 카카오 로그아웃
    public String sendKaKaoLogout(String userId) {
        String value = "";
        String revInfo = "https://kapi.kakao.com/v1/user/logout";

        RestTemplate restTemplate = new RestTemplate();

        Member member = memberRepository.findByEmailAndSocialYn(userId,"Y").get();
        System.out.println("member = " + member);
        // Set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + member.getSocialToken());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(headers);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(revInfo,entity,String.class);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(forEntity.getBody().toString());

            value =  jsonObject.get("id").toString();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return value;
    }

}
