package com.security.springsecuritytest.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireBaseUserRepo extends JpaRepository<FireBaseUser,String>{
    Optional<FireBaseUser> findByEmail(String email);
    Optional<FireBaseUser> findByUID(String UID);
}
