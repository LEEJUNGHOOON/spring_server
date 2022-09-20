package com.security.springsecuritytest.domain.userInfoDetail;

import com.security.springsecuritytest.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUserinfo_Id(Long id); // 이메일 통해 회원 조회하기 위함

}
