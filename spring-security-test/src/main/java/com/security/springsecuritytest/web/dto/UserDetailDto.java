package com.security.springsecuritytest.web.dto;

import com.security.springsecuritytest.domain.user.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDetailDto {

    private List<String> favfood;
    private List<String> foodtaste;
    private int howmany;
    private UserInfo userinfo;
}
