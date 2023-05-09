package com.security.springsecuritytest.web.dto;

import com.security.springsecuritytest.domain.recipeList.RecipeList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RecipeReviewDto {

    private String UID;
    private RecipeList recipeList;
    private int reviewPoint;

    @Builder
    public RecipeReviewDto(String UID,RecipeList recipeList, int reviewPoint){
        this.UID = UID;
        this.recipeList = recipeList;
        this.reviewPoint = reviewPoint;
    }

}
