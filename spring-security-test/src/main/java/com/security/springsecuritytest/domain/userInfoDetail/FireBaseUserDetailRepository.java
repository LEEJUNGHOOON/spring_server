package com.security.springsecuritytest.domain.userInfoDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FireBaseUserDetailRepository extends JpaRepository<FireBaseUserDetail, String> {
    Optional<FireBaseUserDetail> findByUID(String uid);
}
