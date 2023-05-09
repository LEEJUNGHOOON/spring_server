package com.security.springsecuritytest.service;

import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.web.dto.RecipeDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeDetailService {
    private final RecipedetailRepo recipedetailRepo;//레시피 조리방법 레포지토리

    public int save(RecipeDetailDto recipeDetailDto){//레시피 조리 방법을 db에 저장
        Recipedetail recipedetail = Recipedetail.builder()
                .idx(recipeDetailDto.getIdx())
                .cooking_order(recipeDetailDto.getCooking_order())
                .cooking_order_no(recipeDetailDto.getCooking_order_no())
                .recipeList(recipeDetailDto.getRecipeList()).build();

        return recipedetailRepo.save(recipedetail).getIdx();//저장한 값의 인덱스를 return
    }
}
