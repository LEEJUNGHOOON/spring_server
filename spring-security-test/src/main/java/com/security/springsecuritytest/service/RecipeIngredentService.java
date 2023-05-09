package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RecipeIngredentService {

    private final RecipeIngredientRepo recipeIngredientRepo;//레시피 재료 레포지토리

    public int save(RecipeIngredientDto recipeIngredientDto){//레시피 재료을 db에 저장
        Recipeingredient recipeingredient = Recipeingredient.builder()
                .idx_ing(recipeIngredientDto.getIdx_ing())
                .ingredient_name(recipeIngredientDto.getIngredient_name())
                .ingredient_Cp(recipeIngredientDto.getIngredient_Cp())
                .recipeList(recipeIngredientDto.getRecipeList()).build();
        return recipeIngredientRepo.save(recipeingredient).getIdx_ing();
    }

}
