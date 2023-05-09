package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.recipeList.RecipeListRepo;
import com.security.springsecuritytest.web.dto.RecipeListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeListService {

    private final RecipeListRepo recipeListRepo;//전체 레시피 레포지토리

    public int save(RecipeListDto recipeListDto){//레시피정보를 db에 저장
        RecipeList recipeList = RecipeList.builder()
                .ID(recipeListDto.getID())
                .Name(recipeListDto.getName())
                .imgsrc(recipeListDto.getImgsrc())
                .recipe_tag(recipeListDto.getRecipe_tag()).build();
        return recipeListRepo.save(recipeList).getID();
    }
}
