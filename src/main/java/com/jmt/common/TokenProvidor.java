package com.jmt.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jmt.common.ExpiredTime.EXPIRED_TIMEOUT;

@Service
public class TokenProvidor {
    // jmt-final, algorithm : HS256
    // jmt-final-access
    private static final String ACCESS_SECRET_KEY = "eyJhbGciOiJIUzUxMiJ91eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NzY0NzE4MSwiaWF0IjoxNjk3NjQ3MTgxfQ13wHTrWXNFadbLGEuGnQsIdErKSSJE1RuhW2THwecAu4S4SEqnaLOEhYr1cN8PHHfSe9C1U1nkwPogwMZwY0OGA";
    // jmt-final-refresh
    private static final String REFRESH_SECRET_KEY = "eyJhbGciOiJIUzUxMiJ91eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NzY0NzE4MSwiaWF0IjoxNjk3NjQ3MTgxfQ1X71JyPCK1sNclwRBWGPZI24L50giCi2FR3wPSt8uDTvS613EAdJkfiqneK2FAeb1WPQ5EmlT3bTvU6DPgyyJBQ";

    // 보통 30분으로 작성
    public String createAcessToken(String userId) {
        Date now = new Date();
        Map<String, Object> headerMap = new HashMap<>();

        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS512");

        System.out.println("createAccessToken");

        return Jwts.builder()
                .setHeader(headerMap)                                               // Header
                .claim("userId", userId)                                         // payload 정보
                .setIssuedAt(now)                                                   // 발행시간
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRED_TIMEOUT)) // 만료기간
                .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET_KEY)              // algorithem
                .compact();
    }
    
    // 보통 2Week로 설정
    public String createRefreshToken(String userId) {
        Date now = new Date();

        Map<String, Object> headerMap = new HashMap<>();

        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS512");

        return Jwts.builder()
                .setHeader(headerMap)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*14))) // 만료기간 2주
                .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET_KEY)
                .compact();
        
    }

    public Boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_SECRET_KEY).build().
                    parseClaimsJws(token);



        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(REFRESH_SECRET_KEY).build().
                    parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getUserId(String token) {
        System.out.println("text = " + Jwts.parserBuilder().setSigningKey(ACCESS_SECRET_KEY).build().
                parseClaimsJws(token).getBody().get("userId"));
        return Jwts.parserBuilder().setSigningKey(ACCESS_SECRET_KEY).build().parseClaimsJws(token).getBody().get("userId").toString();
    }

    public String parseJwt(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        System.out.println("request = " + request);

        if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        return null;
    }

}
