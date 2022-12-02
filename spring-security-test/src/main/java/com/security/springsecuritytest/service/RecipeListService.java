package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.recipeList.RecipeListRepo;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetail;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetailRepository;
import com.security.springsecuritytest.web.dto.RecipeListDto;
import com.security.springsecuritytest.web.dto.UserDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeListService {

    private final RecipeListRepo recipeListRepo;

    public int save(RecipeListDto recipeListDto){
        RecipeList recipeList = RecipeList.builder()
                .Name(recipeListDto.getName())
                .imgsrc(recipeListDto.getImgsrc()).build();
        return recipeListRepo.save(recipeList).getID();
    }
}
