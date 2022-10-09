package com.security.springsecuritytest.domain.recipeIngredient;

import com.security.springsecuritytest.domain.user.UserInfo;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "recipeingredient")
public class Recipeingredient {

    @Column
    private int RECIPE_ID;
    @Id
    @Column
    private int IRDNT_SN;
    @Column
    private String IRDNT_NM;
    @Column
    private String IRDNT_CPCTY;
    @Column
    private int IRDNT_TY_CODE;
    @Column
    private String IRDNT_TY_NM;

    @Builder
    public Recipeingredient(int RECIPE_ID,int IRDNT_SN,String IRDNT_NM,String IRDNT_CPCTY,int IRDNT_TY_CODE,String IRDNT_TY_NM){
        this.RECIPE_ID = RECIPE_ID;
        this.IRDNT_SN = IRDNT_SN;
        this.IRDNT_NM = IRDNT_NM;
        this.IRDNT_CPCTY = IRDNT_CPCTY;
        this.IRDNT_TY_CODE =IRDNT_TY_CODE;
        this.IRDNT_TY_NM = IRDNT_TY_NM;
    }
}
