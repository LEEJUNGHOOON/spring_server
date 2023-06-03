package com.security.springsecuritytest.web.controller;

import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.recipeList.RecipeListRepo;
import com.security.springsecuritytest.service.RecipeDetailService;
import com.security.springsecuritytest.service.RecipeIngredentService;
import com.security.springsecuritytest.service.RecipeListService;
import com.security.springsecuritytest.web.dto.RecipeDetailDto;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import com.security.springsecuritytest.web.dto.RecipeListDto;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/android")
public class RecipeSaveController {
    @Autowired
    private final RecipeListRepo recipeListRepo;//전체레시피
    @Autowired
    private final RecipeIngredientRepo recipeIngredientRepo;//해당레시피의 재료등
    @Autowired
    private final RecipedetailRepo recipedetailRepo;//해당레시피의 조리순서
    @Autowired
    private final RecipeListService recipeListService;
    @Autowired
    private final RecipeIngredentService recipeIngredentService;
    @Autowired
    private final RecipeDetailService recipeDetailService;

    @ResponseBody
    @PostMapping("/saveRecipe")//레시피를 DB에 저장
    public String saveRecipe(@RequestBody String jsondata) throws ParseException {

        System.out.println(jsondata);
        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsondata);//안드로이드 app에서 사용자가 입력한 레시피의 정보를 parameter로 받아와 json화
        System.out.println(json);

        JSONObject recipe_info = (JSONObject)json.get("recipe_info");
        //1.
        int ID = (int)recipeListRepo.count();//현재 전체 레시피의 몇개의 data들이 있는지 파악
        String name = (String)recipe_info.get("Name");//레시피의 이름
        String url = (String)recipe_info.get("Url");//레시피 사진의 주소
        String tag = (String)recipe_info.get("Tag");//레시피 사진의 주소
        RecipeListDto recipeListDto = new RecipeListDto(ID,name,url,tag);//여기서 post param으로받은 이름,url,자동으로생성되는 id값()
        int result = recipeListService.save(recipeListDto);//recipe_list(total_list)에 저장

        //2.
        JSONArray recipe_ingredient = (JSONArray)json.get("recipe_ingredient");//사용자가 입력한 레시피 재료 정보

        int ingredient_id_count= Integer.parseInt(String.valueOf(recipeIngredientRepo.count()));//레시피 재료 db에 얼마나 많은 data가 있는지 파악

        for(Object JsonRecipeIngredient: recipe_ingredient){
            JSONObject recipeIngredient = (JSONObject)JsonRecipeIngredient;//json 형태로 변형해줌
            System.out.println(recipeIngredient);

            String ingredient_Name = (String)recipeIngredient.get("ingredient_Name");
            String getIngredient_Cp = (String)recipeIngredient.get("getIngredient_Cp");
            Optional<RecipeList> recipeList = recipeListRepo.findById(result);
            RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredient_id_count,ingredient_Name,
                    getIngredient_Cp,recipeList.get());//받아온 정보도 ingredient Dto 생성

            recipeIngredentService.save(recipeIngredientDto);//DB에 저장
            ingredient_id_count++;//db에 들어있는 재료 data 1개 추가
        }
        //3.
        JSONArray recipe_cooking = (JSONArray)json.get("recipe_cooking");

        int cooking_id_count= Integer.parseInt(String.valueOf(recipedetailRepo.count()));
        System.out.println("recipe detail count: "+ cooking_id_count);

        for(Object JsonRecipeCooking: recipe_cooking){
            JSONObject recipeCooking = (JSONObject)JsonRecipeCooking;//json 형태로 변형해줌
            System.out.println(recipeCooking);

            String cooking_order = (String)recipeCooking.get("cooking_order");
            int cooking_order_no = Integer.parseInt(String.valueOf(recipeCooking.get("cooking_order_no")));
            Optional<RecipeList> recipeList = recipeListRepo.findById(result);
            RecipeDetailDto recipeDetailDto = new RecipeDetailDto(cooking_id_count,cooking_order,cooking_order_no,recipeList.get());

            recipeDetailService.save(recipeDetailDto);//DB에 저장
            cooking_id_count++;
        }

        return "1";//잘 저장되어있을경우
    }
}
