package com.security.springsecuritytest.web.dto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FireBaseUserDto {

    private String UID; // PK(from firebase)
    private String email; // id(email)
    private String password; // 비밀번호
    private String auth; // role , 형태로 저장

    @Builder
    public FireBaseUserDto(String UID,String email, String password, String auth) {
        this.UID = UID;
        this.email = email;
        this.password = password;
        this.auth = auth;
    }

}
