package com.security.springsecuritytest.domain.recipeDetail;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name = "recipedetail")
public class Recipedetail {

    @Column
    private int RECIPE_ID;
    @Id
    @Column
    private int COOKING_NO;
    @Column
    private String COOKING_DC;
    @Column
    private String STRE_STEP_IMAGE_URL;
    @Column
    private String STEP_TIP;

    @Builder
    public Recipedetail(int RECIPE_ID, int COOKING_NO, String COOKING_DC, String STRE_STEP_IMAGE_URL, String STEP_TIP){
        this.RECIPE_ID = RECIPE_ID;
        this.COOKING_NO = COOKING_NO;
        this.COOKING_DC = COOKING_DC;
        this.STRE_STEP_IMAGE_URL = STRE_STEP_IMAGE_URL;
        this.STEP_TIP =STEP_TIP;
    }
}
