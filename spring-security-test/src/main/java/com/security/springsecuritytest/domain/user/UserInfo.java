package com.security.springsecuritytest.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Entity
@Getter
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code; // PK

    @Column(unique = true, nullable = false)
    private String email; // id(email)

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String auth; // role , 형태로 저장

    @Builder
    public UserInfo(String email, String password, String auth) {
        this.email = email;
        this.password = password;
        this.auth = auth;
    }

    // 필수 override 메소드들 구현

    // 사용자의 권한이 ,로 구분되어 있는 auth을 받음, 콜렉션 형태로 반환시킴
    // 단, 자료형은 GrantedAuthority를 구현해야 함함
   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for(String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 unique한 값 return(보통 pk or id)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 password 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료되지 않음
    }

    // 계정 잠김 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠기지 않음
    }

    // 비밀번호 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않음
    }

    // 계정의 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // 활성화 됨
    }


}
