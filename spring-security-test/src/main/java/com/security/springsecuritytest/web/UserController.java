package com.security.springsecuritytest.web;

import com.security.springsecuritytest.domain.user.UserInfo;
import com.security.springsecuritytest.domain.user.UserRepository;
import com.security.springsecuritytest.domain.userTest.UserInfoTest;
import com.security.springsecuritytest.service.UserDetailService;
import com.security.springsecuritytest.service.UserInfoTestService;
import com.security.springsecuritytest.service.UserService;
import com.security.springsecuritytest.web.dto.UserDetailDto;
import com.security.springsecuritytest.web.dto.UserInfoDto;
import com.security.springsecuritytest.web.dto.UserInfoTestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserInfoTestService userInfoTestService;


    @ResponseBody
    @PostMapping("/idcheck")//회원가입시 아이디 중복체크
    public String idcheck(@RequestBody String email) throws ParseException {

        System.out.println(email);
        String msg="";
        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(email);
        
        String email1 = (String) json.get("email");

        Optional<UserInfo> userInfo = Optional.ofNullable(userService.loadUserByUsername(email1));

        if(userInfo.isPresent())
            msg = "0";
        else
            msg = "1";

        return msg;
    }

    @PostMapping("/signup") // signup api
    public String signup(UserInfoTestDto infoDto) {
        userInfoTestService.save(infoDto);
        return "redirect:/login";
    }

    @GetMapping("/logout") // logout by GET 요청
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());
        return "redirect:/login";
    }

}
