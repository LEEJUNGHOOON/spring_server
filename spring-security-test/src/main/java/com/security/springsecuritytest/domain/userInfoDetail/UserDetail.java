package com.security.springsecuritytest.domain.userInfoDetail;

import com.security.springsecuritytest.domain.user.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="userdetail")
public class UserDetail {

    @Id
    @GeneratedValue
    private Long temp;

    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> favfood;

    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> foodtaste;

    @Column
    private int howmany;

    @OneToOne()
    @JoinColumn(name="userinfo_id")
    private UserInfo userinfo;

    @Builder
    public UserDetail(List<String> favfood, List<String> foodtaste, int howmany,UserInfo userinfo){
        this.favfood = favfood;
        this.foodtaste = foodtaste;
        this.howmany = howmany;
        this.userinfo = userinfo;
    }
}
