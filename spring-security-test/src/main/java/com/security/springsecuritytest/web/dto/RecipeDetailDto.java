package com.security.springsecuritytest.web.dto;

import lombok.Builder;

public class RecipeDetailDto {

    private int RECIPE_ID;
    private int IRDNT_SN;
    private String IRDNT_NM;
    private String IRDNT_CPCTY;
    private int IRDNT_TY_CODE;
    private String IRDNT_TY_NM;

    @Builder
    public RecipeDetailDto(int RECIPE_ID,int IRDNT_SN,String IRDNT_NM,String IRDNT_CPCTY,int IRDNT_TY_CODE,String IRDNT_TY_NM ){
        this.RECIPE_ID = RECIPE_ID;
        this.IRDNT_SN = IRDNT_SN;
        this.IRDNT_NM = IRDNT_NM;
        this.IRDNT_CPCTY = IRDNT_CPCTY;
        this.IRDNT_TY_CODE = IRDNT_TY_CODE;
        this.IRDNT_TY_NM = IRDNT_TY_NM;
    }
}
