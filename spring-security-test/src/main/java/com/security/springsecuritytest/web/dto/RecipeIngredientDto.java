package com.security.springsecuritytest.web.dto;

import com.security.springsecuritytest.domain.recipeList.RecipeList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
public class RecipeIngredientDto {;
    private int idx_ing;
    private String ingredient_name;
    private RecipeList recipeList;

    @Builder
    public RecipeIngredientDto(int idx_ing,String ingredient_name,RecipeList recipeList){
        this.idx_ing = idx_ing;
        this.ingredient_name = ingredient_name;
        this.recipeList = recipeList;
    }
}
