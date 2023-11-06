package com.jmt.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 여러번의 인증을 방지하기 위해 OncePerRequestFilter 사용
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvidor tokenProvidor;
    public static String tokenUserId = "";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = tokenProvidor.parseJwt(request);

        System.out.println("jwt = " + jwt);


        if(jwt != null && tokenProvidor.validateAccessToken(jwt)) {
            System.out.println("들어옴???");
            tokenUserId = tokenProvidor.getUserId(jwt);
            Authentication auth = new UsernamePasswordAuthenticationToken(tokenUserId,null, AuthorityUtils.NO_AUTHORITIES); // AuthorityUtils.NO_AUTHORITIES : 권한 비교가 없는 것
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        System.out.println(" 멈춤??? ");
        filterChain.doFilter(request,response); // 다음 Filter로 넘겨줘야하므로 사용
    }
}
