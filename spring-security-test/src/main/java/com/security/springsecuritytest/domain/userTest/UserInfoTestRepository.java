package com.security.springsecuritytest.domain.userTest;

import com.security.springsecuritytest.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoTestRepository extends JpaRepository<UserInfoTest, Integer> {
    Optional<UserInfoTest> findByEmail(String email); // 이메일 통해 회원 조회하기 위함
}
