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


    public List<RecipeIngredientDto> getRecipeList(){
        List<Recipeingredient> ris = recipeIngredientRepo.findAll();
        List<RecipeIngredientDto> riDtoList = new ArrayList<>();

        for(Recipeingredient ri: ris){
            RecipeIngredientDto recipeIngredientDto = RecipeIngredientDto.builder()
                    .RECIPE_ID(ri.getRECIPE_ID())
                    .IRDNT_SN(ri.getIRDNT_SN())
                    .IRDNT_NM(ri.getIRDNT_NM())
                    .IRDNT_CPCTY(ri.getIRDNT_CPCTY())
                    .IRDNT_TY_CODE(ri.getIRDNT_TY_CODE())
                    .IRDNT_TY_NM(ri.getIRDNT_TY_NM())
                    .build();
            riDtoList.add(recipeIngredientDto);
        }
        return riDtoList;
    }

}
