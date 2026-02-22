package com.club.manager.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정 명세서 - 스프링의 환경 설정 파일 역할을 함
@EnableWebSecurity // Spring Security를 활성화하겠다는 의미 / 모든 요청은 Spring Security를 거쳐야함
public class SecurityConfig {
    
    // 비밀번호를 암호화해주는 BCrypt를 스프링 컨테이너에 등록
    // - 복호화를 못함
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티의 기본 방어막(Filter) 설정을 커스텀함
    // 규칙 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API 서버(CSR 방식)이므로 CSRF 공격 방어는 일단 끕니다.
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 지금은 개발 초기니 모든 API 접근을 100% 허용합니다!
            );
        
        return http.build();
    }

}
