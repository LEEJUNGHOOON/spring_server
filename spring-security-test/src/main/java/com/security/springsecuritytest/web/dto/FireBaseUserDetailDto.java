package com.security.springsecuritytest.web.dto;

import com.security.springsecuritytest.domain.user.FireBaseUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class FireBaseUserDetailDto {

    private String UID;
    private int lastfood;
    private String foodtaste;
    private ArrayList<Integer> userRecipeList;
    private FireBaseUser userinfo;

    @Builder
    public FireBaseUserDetailDto(String UID,int lastfood, String foodtaste,ArrayList<Integer> userRecipeList,FireBaseUser userinfo){
        this.UID = UID;
        this.lastfood = lastfood;
        this.foodtaste = foodtaste;
        this.userRecipeList = userRecipeList;
        this.userinfo = userinfo;
    }
}
