package com.security.springsecuritytest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.recipeList.RecipeListRepo;
import com.security.springsecuritytest.domain.user.FireBaseUser;
import com.security.springsecuritytest.domain.userInfoDetail.FireBaseUserDetail;
import com.security.springsecuritytest.domain.userInfoDetail.FireBaseUserDetailRepository;
import com.security.springsecuritytest.service.FireBaseUserService;
import com.security.springsecuritytest.service.RecipeDetailService;
import com.security.springsecuritytest.service.RecipeIngredentService;
import com.security.springsecuritytest.service.RecipeListService;
import com.security.springsecuritytest.web.dto.FireBaseUserDetailDto;
import com.security.springsecuritytest.web.dto.FireBaseUserDto;
import com.security.springsecuritytest.web.dto.RecipeDetailDto;
import com.security.springsecuritytest.web.dto.RecipeIngredientDto;
import com.security.springsecuritytest.web.dto.RecipeListDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/android")
public class recipetoappController {

    @Autowired
    private final RecipeListRepo recipeListRepo;//전체레시피
    private final RecipeIngredientRepo recipeIngredientRepo;//해당레시피의 재료등
    private final RecipedetailRepo recipedetailRepo;//해당레시피의 조리순서

    @ResponseBody
    @PostMapping("/recipeFind")//레시피 전체 리스트를 post형식으로 안드로이드에게 보내줌
    public JSONObject recipeListFind(@RequestParam("id") int id ){
        System.out.println("서버에서 안드로이드 접속 요청함(특정 레시피 리스트)");

        JSONObject recipejson= new JSONObject();

        try{

            Optional<RecipeList> rdList = recipeListRepo.findById(id);//db에서 모든레시피들을 가져옴

            if(rdList.isPresent()){

                RecipeList tmp = rdList.get();
                recipejson.put("ID", tmp.getID());
                recipejson.put("Name",tmp.getName());
                recipejson.put("imgsrc", tmp.getImgsrc());
                recipejson.put("recipe_tag",tmp.getRecipe_tag());

                return recipejson;

            }else{

                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping("/recipeList")//레시피 전체 리스트를 post형식으로 안드로이드에게 보내줌//
    public JSONObject recipeList(){//조건없이 모든 레시피를 리턴해줌
        System.out.println("서버에서 안드로이드 접속 요청함(레시피 전체 리스트)");

        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();

        try{
            List<RecipeList> rdList = (List<RecipeList>)recipeListRepo.findAll();

            for (RecipeList recipeList:rdList) {
                System.out.println(recipeList.getClass());

                Gson gson = new Gson();

                String jsonString = gson.toJson(recipeList);

                JSONObject json=new JSONObject();
                json = (JSONObject)jsonParser.parse(jsonString);
                jsonArray.add(json);
            }
            System.out.println();
            recipejson.put("haha",jsonArray);


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return recipejson;//모든레시피리스트를 json으로 리턴해줌
    }

    @ResponseBody
    @PostMapping("/recipeIngredient")//Db에서 레시피 재료들을 post형식으로 안드로이드에게 보내줌
    public JSONObject recipeIngredient(HttpServletRequest request){
        System.out.println("서버에서 안드로이드 접속 요청함(레시피 재료)");


        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();

        try{
            String id = request.getParameter("id");//사용자가 누른 레시피의 id값을 parameter로 가져옴
            System.out.println("id:"+id);
            List<Recipeingredient> rilist = recipeIngredientRepo.findByRecipeLList(Integer.parseInt(id));//
            System.out.println(rilist.get(0).getIngredient_name());


            for (Recipeingredient recipeingredient:rilist) {
                System.out.println(recipeingredient.getClass());
                Gson gson = new Gson();
                String jsonString = gson.toJson(recipeingredient);

                JSONObject json=new JSONObject();
                json = (JSONObject)jsonParser.parse(jsonString);
                jsonArray.add(json);
            }
            System.out.println();
            recipejson.put("recipeIngredient",jsonArray);


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return recipejson;
    }

    @ResponseBody
    @PostMapping("/recipeCooking")//Db에서 레시피 조리 순서를 post형식으로 안드로이드에게 보내줌
    public JSONObject androidPage(HttpServletRequest request){
        System.out.println("서버에서 안드로이드 접속 요청함(레시피 순서)");
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();

        try{

            String id = request.getParameter("id");//사용자가 누른 레시피의 id값을 parameter로 가져옴
            System.out.println("id:"+id);
            List<Recipedetail> rdList = recipedetailRepo.findByRecipeLList(Integer.parseInt(id));//해당하는 id의 레시피 조리순서들을 가져옴

            for (Recipedetail recipedetail1:rdList) {
                System.out.println(recipedetail1.getClass());

                Gson gson = new Gson();

                String jsonString = gson.toJson(recipedetail1);

                JSONObject json=new JSONObject();
                json = (JSONObject)jsonParser.parse(jsonString);
//                System.out.println(json instanceof JSONObject);
                jsonArray.add(json);
            }
            System.out.println();
            recipejson.put("recipeCooking",jsonArray);


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return sortjson(recipejson);
    }

    @Autowired
    private final RecipeListService recipeListService;
    @Autowired
    private final RecipeIngredentService recipeIngredentService;
    @Autowired
    private final RecipeDetailService recipeDetailService;

    @ResponseBody
    @PostMapping("/saveRecipe")//레시피를 DB에 저장
    public String saveRecipe(@RequestBody String jsondata) throws ParseException{

        System.out.println(jsondata);
        String msg="";
        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsondata);
        System.out.println(json);


        JSONObject recipe_info = (JSONObject)jsonParser.parse((String)json.get("recipe_info"));

        int ID = (int)recipeListRepo.count();
        String name = (String)recipe_info.get("Name");
        String url = (String)recipe_info.get("Url");
        RecipeListDto recipeListDto = new RecipeListDto(ID,name,url);//여기서 post param으로받은 이름,url,자동으로생성되는 id값()
        int result = recipeListService.save(recipeListDto);//recipe_list(total_list)에 저장

        JSONArray recipe_ingredient = (JSONArray)json.get("recipe_ingredient");

        int ingredient_id_count= Integer.parseInt(String.valueOf(recipeIngredientRepo.count()));

        for(Object JsonRecipeIngredient: recipe_ingredient){
            JSONObject recipeIngredient = (JSONObject)jsonParser.parse((String)JsonRecipeIngredient);
            System.out.println(recipeIngredient);

            String ingredient_Name = (String)recipeIngredient.get("ingredient_Name");
            String getIngredient_Cp = (String)recipeIngredient.get("getIngredient_Cp");
            Optional<RecipeList> recipeList = recipeListRepo.findById(result);
            RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredient_id_count,ingredient_Name,
                    getIngredient_Cp,recipeList.get());

            recipeIngredentService.save(recipeIngredientDto);//여기에 로직추가
            ingredient_id_count++;
        }

        JSONArray recipe_cooking = (JSONArray)json.get("recipe_cooking");

        int cooking_id_count= Integer.parseInt(String.valueOf(recipedetailRepo.count()));
        System.out.println("recipe detail count: "+ cooking_id_count);


        for(Object JsonRecipeCooking: recipe_cooking){
            JSONObject recipeCooking = (JSONObject)jsonParser.parse((String)JsonRecipeCooking);
            System.out.println(recipeCooking);

            String cooking_order = (String)recipeCooking.get("cooking_order");
            int cooking_order_no = Integer.parseInt(String.valueOf(recipeCooking.get("cooking_order_no")));
            Optional<RecipeList> recipeList = recipeListRepo.findById(result);
            RecipeDetailDto recipeDetailDto = new RecipeDetailDto(cooking_id_count,cooking_order,cooking_order_no,recipeList.get());

            recipeDetailService.save(recipeDetailDto);//여기에 로직추가
            cooking_id_count++;
        }

        return Integer.toString(ID);//잘 저장되어있을경우
    }

    public JSONObject sortjson(JSONObject json){//레시피 순서를 id값을 이용하여 sorting해주는 함수
        JSONParser parser = new JSONParser();

        JSONObject sortedjson = new JSONObject();

        JSONArray arr = (JSONArray)json.get("recipe");
        List<JSONObject> copyList = new ArrayList<JSONObject>();

        for (int i=0; i<arr.size(); i++){
            copyList.add((JSONObject) arr.get(i)); // list 에 삽입 실시
        }
        Collections.sort( copyList , new Comparator<JSONObject>() {
            private static final String KEY = "step"; // TODO [정렬하려는 jsonObject key]
            @Override
            public int compare(JSONObject a, JSONObject b) {

                // TODO [정수값 value 기준 정렬]
                int value_A = 0;
                int value_B = 0;

                // TODO [문자열값 value 기준 정렬]
                // String value_A = "";
                // String value_B = "";
                try {
                    // TODO [정수값 value 기준 정렬]
                    value_A = Integer.parseInt(String.valueOf(a.get("cooking_order_no"))); // [정렬하려는 key 지정]
                    value_B = Integer.parseInt(String.valueOf(b.get("cooking_order_no"))); // [정렬하려는 key 지정]

                    // TODO [문자열값 value 기준 정렬]
                    // value_A = (String) a.get(KEY); // [정렬하려는 key 지정]
                    // value_B = (String) b.get(KEY); // [정렬하려는 key 지정]
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                // TODO [정수값 value 기준 정렬]
                return Integer.compare(value_A, value_B);


                // TODO [문자열값 value 기준 정렬]
                //return value_A.compareTo(value_B);
            }
        });

        JSONArray sortedArray = new JSONArray();
        for (JSONObject item:copyList) {

            sortedArray.add(item);
        }

        sortedjson.put("recipe",sortedArray);

        System.out.println(sortedjson);
        return sortedjson;
    }

    @Autowired
    private final FireBaseUserService fireBaseUserService;

    @ResponseBody
    @PostMapping("/saveNewUser")
    public String saveNewUser(@RequestBody String jsondata) throws ParseException{
        System.out.println(jsondata);
        String msg="";
        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsondata);
        System.out.println(json);

        JSONObject user_info = (JSONObject)json.get("user_info");

        String UID = (String)user_info.get("UID");
        String email = (String)user_info.get("email");
        String password = (String)user_info.get("password");
        String auth = (String)user_info.get("auth");

        FireBaseUserDto userDto = FireBaseUserDto.builder()
                .UID(UID)
                .auth(auth)
                .email(email)
                .password(password).build();
        
        FireBaseUser user = fireBaseUserService.saveNewFireBaseUser(userDto);

        if(user == null){
            return "-1";
        }

        ArrayList newUserRecipeList = new ArrayList<Integer>();

        FireBaseUserDetailDto userDetailDto = FireBaseUserDetailDto.builder()
                .UID(user.getUID())
                .lastfood(-1)
                .foodtaste(null)
                .userRecipeList(newUserRecipeList)
                .userinfo(user).build();

        FireBaseUserDetail userDetail = fireBaseUserService.saveNewFireBaseUser(userDetailDto);

        if(userDetail == null){
            return "-1";
        }

        return "1";

    }

    @ResponseBody
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody String jsondata) throws ParseException{
        System.out.println(jsondata);
        String msg="";
        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsondata);
        System.out.println(json);

        JSONObject user_info = (JSONObject)json.get("user_info");

        String UID = (String)user_info.get("UID");
        String email = (String)user_info.get("email");
        String password = (String)user_info.get("password");
        String auth = (String)user_info.get("auth");

        FireBaseUserDto userDto = FireBaseUserDto.builder()
                .UID(UID)
                .auth(auth)
                .email(email)
                .password(password).build();
        
        String result = fireBaseUserService.updateFireBaseUser(userDto);

        if(result == null){
            return "-1";
        }

        return "1";

    }
    
    @ResponseBody
    @PostMapping("/updateUserDetail")
    public String updateUserDetail(@RequestBody String jsondata) throws ParseException{
        System.out.println(jsondata);
        String msg="";
        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsondata);
        System.out.println(json);

        JSONObject user_info = (JSONObject)json.get("user_detail");

        String UID = (String)user_info.get("UID");
        int lastfood = (int)((long)user_info.get("lastfood"));
        String foodtaste = (String)user_info.get("foodtaste");
        
        JSONArray listobject = (JSONArray)user_info.get("userRecipeList");
        ArrayList<Integer> userRecipeList = new ArrayList<Integer>();

        for(Object temp : listobject){
            JSONObject obj = (JSONObject) temp;

            int tmp_ID = (int)((long)obj.get("ID"));

            userRecipeList.add(tmp_ID);
        }

        FireBaseUserDetailDto userDetailDto = FireBaseUserDetailDto.builder()
                .UID(UID)
                .lastfood(lastfood)
                .foodtaste(foodtaste)
                .userRecipeList(userRecipeList).build();
        
        String result = fireBaseUserService.updateFireBaseUserDetail(userDetailDto);

        if(result == null){
            return "-1";
        }

        return "1";

    }

}
