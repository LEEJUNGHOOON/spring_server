package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetailRepository;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeIngredentService {

    private final RecipeIngredientRepo recipeIngredientRepo;

    public int save(RecipeIngredientDto recipeIngredientDto){
        Recipeingredient recipeingredient = Recipeingredient.builder()
                .idx_ing(recipeIngredientDto.getIdx_ing())
                .ingredient_name(recipeIngredientDto.getIngredient_name())
                .ingredient_Cp(recipeIngredientDto.getIngredient_Cp())
                .recipeList(recipeIngredientDto.getRecipeList()).build();

        return recipeIngredientRepo.save(recipeingredient).getIdx_ing();
    }


    public List<RecipeIngredientDto> getRecipeList(){
        List<Recipeingredient> ris = recipeIngredientRepo.findAll();
        List<RecipeIngredientDto> riDtoList = new ArrayList<>();

        for(Recipeingredient ri: ris){
            RecipeIngredientDto recipeIngredientDto = RecipeIngredientDto.builder()
                    .idx_ing(ri.getIdx_ing())
                    .ingredient_name(ri.getIngredient_name())
                    .ingredient_Cp(ri.getIngredient_Cp())
                    .recipeList(ri.getRecipeList())
                    .build();
            riDtoList.add(recipeIngredientDto);
        }
        return riDtoList;
    }

}
