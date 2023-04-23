package com.security.springsecuritytest.domain.userInfoDetail;

import com.security.springsecuritytest.domain.user.FireBaseUser;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Entity
@Getter
@Setter
@Table(name="firebaseuserdetail")
public class FireBaseUserDetail {

    @Id
    @Column
    private String UID;

    @Column
    private int lastfood;

    @Column
    private String foodtaste;

    @Column
    private ArrayList<Integer> userRecipeList;

    @OneToOne()
    @JoinColumn(name="firebaseuser")
    private FireBaseUser userinfo;

    @Builder
    public FireBaseUserDetail(String UID,int lastfood, String foodtaste,ArrayList<Integer> userRecipeList,FireBaseUser userinfo){
        this.UID = UID;
        this.lastfood = lastfood;
        this.foodtaste = foodtaste;
        this.userRecipeList = userRecipeList;
        this.userinfo = userinfo;
    }
}
