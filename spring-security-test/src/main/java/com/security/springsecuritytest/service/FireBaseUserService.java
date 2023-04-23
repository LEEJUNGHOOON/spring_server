package com.security.springsecuritytest.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.security.springsecuritytest.domain.user.FireBaseUser;
import com.security.springsecuritytest.domain.user.FireBaseUserRepo;
import com.security.springsecuritytest.domain.userInfoDetail.FireBaseUserDetail;
import com.security.springsecuritytest.domain.userInfoDetail.FireBaseUserDetailRepository;
import com.security.springsecuritytest.web.dto.FireBaseUserDetailDto;
import com.security.springsecuritytest.web.dto.FireBaseUserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FireBaseUserService {
    
    private final FireBaseUserRepo fireBaseUserRepo;
    private final FireBaseUserDetailRepository fireBaseUserDetailRepository;

    public FireBaseUser saveNewFireBaseUser(FireBaseUserDto user){
        FireBaseUser newUser = FireBaseUser.builder()
                .UID(user.getUID())
                .auth(user.getAuth())
                .email(user.getEmail())
                .password(user.getPassword()).build();
        
        fireBaseUserRepo.save(newUser);

        return newUser;

    }

    public FireBaseUserDetail saveNewFireBaseUser(FireBaseUserDetailDto userDetail){

        FireBaseUserDetail newUserDetail = FireBaseUserDetail.builder()
                .UID(userDetail.getUID())
                .lastfood(userDetail.getLastfood())
                .foodtaste(userDetail.getFoodtaste())
                .userRecipeList(userDetail.getUserRecipeList())
                .userinfo(userDetail.getUserinfo()).build();
        
        fireBaseUserDetailRepository.save(newUserDetail);
        
        return newUserDetail;
    }

    public FireBaseUser getFireBaseUser(String UID){
        Optional<FireBaseUser> user = fireBaseUserRepo.findById(UID);

        if(user.isPresent()){
            return user.get();
        }

        return null;
    }

    public FireBaseUserDetail getFireBaseUserDetail(String UID){
        Optional<FireBaseUserDetail> userDetail = fireBaseUserDetailRepository.findById(UID);

        if(userDetail.isPresent()){
            return userDetail.get();
        }

        return null;
    }

    public String updateFireBaseUser(FireBaseUserDto user){
        Optional<FireBaseUser> currentUser = fireBaseUserRepo.findById(user.getUID());

        if(currentUser.isPresent()){
            FireBaseUser _user = FireBaseUser.builder()
                    .UID(user.getUID())
                    .auth(user.getAuth())
                    .email(user.getEmail())
                    .password(user.getPassword()).build();

            return fireBaseUserRepo.save(_user).getUID() + " update clear ";
        }

        return null;
    }

    public String updateFireBaseUserDetail(FireBaseUserDetailDto userDetail){
        Optional<FireBaseUserDetail> currentUserDetail = fireBaseUserDetailRepository.findById(userDetail.getUID());

        if(currentUserDetail.isPresent()){
            FireBaseUserDetail _user = FireBaseUserDetail.builder()
                    .UID(userDetail.getUID())
                    .lastfood(userDetail.getLastfood())
                    .foodtaste(userDetail.getFoodtaste())
                    .userRecipeList(userDetail.getUserRecipeList())
                    .userinfo(currentUserDetail.get().getUserinfo()).build();

            return fireBaseUserDetailRepository.save(_user).getUID() + " Detail update clear ";
        }

        return null;
    }

}
