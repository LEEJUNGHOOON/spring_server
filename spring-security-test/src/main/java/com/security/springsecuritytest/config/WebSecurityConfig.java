package com.security.springsecuritytest.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security를 활성화하는 어노테이션
@Configuration // Bean 관리하는 어노테이션인듯 함, Spring에 대한 공부 더 필요함
// 해당 클래스가 Spring Security 설정 파일로 역할을 하기 위해선 WebSecurityConfigurerAdapter 클래스를 상속해야 함
public class WebSecurityConfig { 
    
    // 유저 정보를 가져올 클래스

    @Bean
    // 인증을 무시할 경로 설정, h2-console 추가 (근데 h2-console에 갑자기 로그인 요구, 수정 필요할듯)
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "h2-console/**","/android/**");
    }

    @Bean
    // 로그인 시 필요한 정보를 가져오기
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}
