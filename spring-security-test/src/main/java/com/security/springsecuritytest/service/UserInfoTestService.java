package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.userTest.UserInfoTest;
import com.security.springsecuritytest.domain.userTest.UserInfoTestRepository;
import com.security.springsecuritytest.web.dto.UserInfoTestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoTestService implements UserDetailsService {

    private final UserInfoTestRepository userInfoTestRepository;

    // UserDetailService 상속시 필수로 구현해야 하는 메소드
    // UserDetails가 기본 반환 타입, UserInfo가 이를 상속하고 있으므로 자동으로 다운캐스팅됨
    @Override
    public UserInfoTest loadUserByUsername(String email) throws UsernameNotFoundException {
        return userInfoTestRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public int save(UserInfoTestDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return userInfoTestRepository.save(UserInfoTest.builder()
                .email(infoDto.getEmail())
                .password(infoDto.getPassword())
                .auth(infoDto.getAuth())
                .name(infoDto.getName()).build()).getId();
    }
}