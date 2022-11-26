package com.security.springsecuritytest.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoTestDto {

    private String email;
    private String password;
    private String auth;
    private String name;
}
