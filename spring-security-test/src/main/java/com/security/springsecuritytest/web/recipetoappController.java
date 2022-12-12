package com.security.springsecuritytest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    private final RecipeListRepo recipeListRepo;
    private final RecipeIngredientRepo recipeIngredientRepo;
    private final RecipedetailRepo recipedetailRepo;

    @ResponseBody
    @PostMapping("/recipeListFind")//레시피 전체 리스트를 post형식으로 안드로이드에게 보내줌
    public JSONObject recipeListFind(HttpServletRequest request){
        System.out.println("서버에서 안드로이드 접속 요청함(레시피 전체 리스트)");

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try{
            InputStream inputStream = request.getInputStream();
            if(inputStream != null){
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while((bytesRead = bufferedReader.read(charBuffer))>0){
                    stringBuilder.append(charBuffer,0,bytesRead);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        body = stringBuilder.toString();

        JSONObject requestBody = null;

        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();

        try{

            requestBody = (JSONObject)jsonParser.parse(body);

            List<RecipeList> rdList = (List<RecipeList>)recipeListRepo.findAll();
            
            JSONArray find_list = (JSONArray)requestBody.get("find");

            System.out.println(find_list);

            ArrayList<Integer> ID_list = new ArrayList<>(); 

            for(int i=0;i<find_list.size();i++){
                JSONObject tmp = (JSONObject)find_list.get(i);

                System.out.println(tmp);
                int ptr = Integer.parseInt(tmp.get("ID").toString());
                System.out.println(ptr);

                ID_list.add(ptr);
            }

            for (RecipeList recipeList:rdList) {

                System.out.println(recipeList.getClass());
                int recipe_id = recipeList.getID();

                Gson gson = new Gson();

                if(ID_list.contains(recipe_id)){
                    String jsonString = gson.toJson(recipeList);

                    JSONObject json=new JSONObject();
                    json = (JSONObject)jsonParser.parse(jsonString);
                    //System.out.println(json instanceof JSONObject);
                    jsonArray.add(json);
                }

            }

            System.out.println();
            recipejson.put("recipelist",jsonArray);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return recipejson;
    }

    @ResponseBody
    @PostMapping("/recipeList")//레시피 전체 리스트를 post형식으로 안드로이드에게 보내줌
    public JSONObject recipeList(HttpServletRequest request){
        System.out.println("서버에서 안드로이드 접속 요청함(레시피 전체 리스트)");

        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();

        try{
            List<RecipeList> rdList = (List<RecipeList>)recipeListRepo.findAll();
            String id = request.getParameter("id");
            System.out.println("id:"+id);

            for (RecipeList recipeList:rdList) {
                System.out.println(recipeList.getClass());

                Gson gson = new Gson();

                String jsonString = gson.toJson(recipeList);

                JSONObject json=new JSONObject();
                json = (JSONObject)jsonParser.parse(jsonString);
//                System.out.println(json instanceof JSONObject);
                jsonArray.add(json);
            }
            System.out.println();
            recipejson.put("recipelist",jsonArray);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return recipejson;
    }

    @ResponseBody
    @PostMapping("/recipeIngredient")//Db에서 레시피 재료들을 post형식으로 안드로이드에게 보내줌
    public JSONObject recipeIngredient(HttpServletRequest request){
        System.out.println("서버에서 안드로이드 접속 요청함(레시피 재료)");


        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();

        try{
            List<Recipeingredient> rdList = (List<Recipeingredient>)recipeIngredientRepo.findAll();
            String id = request.getParameter("id");//사용자가 누른 레시피의 id값을 parameter로 가져옴
            System.out.println("id:"+id);

            int ID = Integer.parseInt(id);

            for (Recipeingredient recipeingredient:rdList) {
                System.out.println(recipeingredient.getClass());

                int tmp_id = recipeingredient.getRecipeList().getID();

                System.out.println(tmp_id);
                System.out.println(ID);

                if(tmp_id == ID){
                    Gson gson = new Gson();

                    String jsonString = gson.toJson(recipeingredient);
                    System.out.println(jsonString);
                    JSONObject json=new JSONObject();

                    json = (JSONObject)jsonParser.parse(jsonString);
                    System.out.println(json instanceof JSONObject);
                    jsonArray.add(json);
                }
            }

            if(jsonArray.isEmpty()){
                recipejson.put("recipeIngredient","null");
                return recipejson;
            }else{
                recipejson.put("recipeIngredient",jsonArray);
            }


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
            List<Recipedetail> rdList = (List<Recipedetail>)recipedetailRepo.findAll();
            String id = request.getParameter("id");//사용자가 누른 레시피의 id값을 parameter로 가져옴
            System.out.println("id:"+id);

            int ID = Integer.parseInt(id);

            for (Recipedetail recipedetail1:rdList) {
                System.out.println(recipedetail1.getClass());

                int tmp_id = recipedetail1.getRecipeList().getID();

                if(tmp_id == ID){
                    Gson gson = new Gson();

                    String jsonString = gson.toJson(recipedetail1);

                    JSONObject json=new JSONObject();
                    json = (JSONObject)jsonParser.parse(jsonString);
    //                System.out.println(json instanceof JSONObject);
                    jsonArray.add(json);
                }
            }

            if(jsonArray.isEmpty()){
                recipejson.put("recipe","null");
                return recipejson;
            }else{
                recipejson.put("recipe",jsonArray);
            }


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


        JSONObject recipe_info = (JSONObject)json.get("recipe_info");

        int ID = (int)recipeListRepo.count()+1;
        String name = (String)recipe_info.get("Name");
        String url = (String)recipe_info.get("Url");
        String Tag = (String)recipe_info.get("Tag");
        RecipeListDto recipeListDto = new RecipeListDto(ID,name,url,Tag);//여기서 post param으로받은 이름,url,자동으로생성되는 id값()
        int result = recipeListService.save(recipeListDto);//recipe_list(total_list)에 저장

        JSONArray recipe_ingredient = (JSONArray)json.get("recipe_ingredient");

        int ingredient_id_count= Integer.parseInt(String.valueOf(recipeIngredientRepo.count()))+1;

        for(Object JsonRecipeIngredient: recipe_ingredient){
            JSONObject recipeIngredient = (JSONObject)JsonRecipeIngredient;
            System.out.println(recipeIngredient);

            String ingredient_Name = (String)recipeIngredient.get("ingredient_Name");
            Optional<RecipeList> recipeList = recipeListRepo.findById(result);
            RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredient_id_count,ingredient_Name,recipeList.get());

            recipeIngredentService.save(recipeIngredientDto);//여기에 로직추가
            ingredient_id_count++;
        }

        JSONArray recipe_cooking = (JSONArray)json.get("recipe_cooking");

        int cooking_id_count= Integer.parseInt(String.valueOf(recipedetailRepo.count()))+1;
        System.out.println("recipe detail count: "+ cooking_id_count);


        for(Object JsonRecipeCooking: recipe_cooking){
            JSONObject recipeCooking = (JSONObject)JsonRecipeCooking;
            System.out.println(recipeCooking);

            String cooking_order = (String)recipeCooking.get("cooking_order");
            int cooking_order_no = Integer.parseInt(String.valueOf(recipeCooking.get("cooking_order_no")));
            Optional<RecipeList> recipeList = recipeListRepo.findById(result);
            RecipeDetailDto recipeDetailDto = new RecipeDetailDto(cooking_id_count,cooking_order,cooking_order_no,recipeList.get());

            recipeDetailService.save(recipeDetailDto);//여기에 로직추가
            cooking_id_count++;
        }

        return "1";//잘 저장되어있을경우
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
}
