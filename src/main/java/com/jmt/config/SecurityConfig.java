package com.jmt.config;

import com.jmt.common.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()                    // Cross Origin Resource Sharing
                .and()
                .csrf().disable()      // rest api시 csrf 보안이 필요 없으므로 off
                .httpBasic().disable() // 로그인 인증창이 뜸
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 Off
                .and()
                .authorizeRequests()
                .antMatchers("/login/**", "/", "/destination/**", "/curator",
                        "/selectSchedule", "/travelSchedule", "/traffic", "/knowledge",
                        "/knowledgeDetail/**", "/noticeBoard/**", "/qnABoard/**",
                        "/joinUser", "/notification/**").permitAll(); // 리소스 접근 인증 절차 없이 허용

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
