package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.web.dto.RecipeDetailDto;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import com.security.springsecuritytest.web.dto.RecipeListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeDetailService {
    private final RecipedetailRepo recipedetailRepo;

    public int save(RecipeDetailDto recipeDetailDto){
        Recipedetail recipedetail = Recipedetail.builder()
                .idx(recipeDetailDto.getIdx())
                .cooking_order(recipeDetailDto.getCooking_order())
                .cooking_order_no(recipeDetailDto.getCooking_order_no())
                .recipeList(recipeDetailDto.getRecipeList()).build();

        return recipedetailRepo.save(recipedetail).getIdx();
    }
}
