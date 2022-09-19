package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.userInfoDetail.UserDetail;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetailRepository;
import com.security.springsecuritytest.web.dto.UserDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService {
    private final UserDetailRepository userDetailRepository;

    public Long save(UserDetailDto userDetailDto){
        UserDetail userDetail = UserDetail.builder()
                .favfood(userDetailDto.getFavfood())
                .foodtaste(userDetailDto.getFoodtaste())
                .howmany(userDetailDto.getHowmany())
                .userinfo(userDetailDto.getUserinfo()).build();
        return userDetailRepository.save(userDetail).getTemp();
    }
}
