package com.security.springsecuritytest.web;

import com.security.springsecuritytest.domain.user.UserInfo;
import com.security.springsecuritytest.service.UserDetailService;
import com.security.springsecuritytest.service.UserService;
import com.security.springsecuritytest.web.dto.UserDetailDto;
import com.security.springsecuritytest.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserDetailController {

    private final UserService userService;
    private final UserDetailService userDetailService;

    @PostMapping("/post")//user추가정보
    public String userDetail(UserDetailDto detailDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        UserInfo userInfo = (UserInfo) userDetails;
        detailDto.setUserinfo(userInfo);
        userDetailService.save(detailDto);
        return "/post";
    }
}
