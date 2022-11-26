package com.security.springsecuritytest.web;

import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.domain.user.UserInfo;
import com.security.springsecuritytest.service.RecipeIngredentService;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import com.security.springsecuritytest.web.dto.UserDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recipe")
public class RecipeIngredientController {

    @Autowired
    private final RecipeIngredientRepo recipeIngredientRepo;
    @Autowired
    private final RecipedetailRepo recipedetailRepo;

    @Transactional
    @GetMapping("/ingredient")//레시피들어가는 재료
    public String recipeingredient(Model model){
        List<Recipeingredient> riList = (List<Recipeingredient>)recipeIngredientRepo.findAll();
        List<Recipeingredient> riList1=  new ArrayList<Recipeingredient>();
        for(Recipeingredient recipeingredient : riList)
        {
            if(recipeingredient.getRecipeList().getID()==0)
            {
                riList1.add(recipeingredient);
            }
        }
        model.addAttribute("recipeIngredient",riList1);
        return "/recipeIngredent";
    }

    @Transactional
    @GetMapping("/detail")//단계별 레시피
    public String recipedetails(Model model){
        List<Recipedetail> rdList = (List<Recipedetail>)recipedetailRepo.findAll();
        model.addAttribute("recipeDetail",rdList);
        return "/recipeDetail";
    }
}
