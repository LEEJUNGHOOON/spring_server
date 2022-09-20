package com.security.springsecuritytest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.springsecuritytest.domain.user.UserInfo;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetail;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetailRepository;
import com.security.springsecuritytest.web.dto.UserDetailDto;
import jdk.internal.org.jline.utils.Log;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Controller
public class JsontransferController {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @GetMapping("/sex")
    @PostMapping("/sex")//user추가정보
    public String springtoflask(UserDetailDto detailDto) {

        String url = "http://127.0.0.1:5000";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            UserInfo userInfo = (UserInfo) userDetails;
            Optional<UserDetail> userDetail= userDetailRepository.findById(userInfo.getId());


            String res = null;
            ObjectMapper mapper = new ObjectMapper();
            res = mapper.writeValueAsString(userDetail);

            bw.write(res);
            bw.flush();
            bw.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/post";
    }
}
