package com.security.springsecuritytest.web.dto;

import com.security.springsecuritytest.domain.recipeList.RecipeList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDetailDto {

    private int idx;
    private String cooking_order;
    private int cooking_order_no;
    private RecipeList recipeList;

    @Builder
    public RecipeDetailDto(int idx,String cooking_order,int cooking_order_no,RecipeList recipeList){
        this.idx = idx;
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
        this.recipeList = recipeList;
    }
}
