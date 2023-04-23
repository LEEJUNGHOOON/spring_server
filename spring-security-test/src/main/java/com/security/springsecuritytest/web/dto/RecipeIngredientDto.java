package com.security.springsecuritytest.web.dto;

import com.security.springsecuritytest.domain.recipeList.RecipeList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDto {;
    private int idx_ing;
    private String ingredient_name;
    private String ingredient_Cp;
    private RecipeList recipeList;

    @Builder
    public RecipeIngredientDto(int idx_ing,String ingredient_name,String ingredient_Cp,RecipeList recipeList){
        this.idx_ing = idx_ing;
        this.ingredient_name = ingredient_name;
        this.ingredient_Cp = ingredient_Cp;
        this.recipeList = recipeList;
    }
}
