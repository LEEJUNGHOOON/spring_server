package com.security.springsecuritytest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.recipeList.RecipeListRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/android")
public class recipetoappController {

    @Autowired
    private final RecipeListRepo recipeListRepo;
    private final RecipeIngredientRepo recipeIngredientRepo;
    private final RecipedetailRepo recipedetailRepo;



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

            for (Recipeingredient recipeingredient:rdList) {
                System.out.println(recipeingredient.getClass());

                Gson gson = new Gson();

                String jsonString = gson.toJson(recipeingredient);

                JSONObject json=new JSONObject();
                json = (JSONObject)jsonParser.parse(jsonString);
//                System.out.println(json instanceof JSONObject);
                jsonArray.add(json);
            }
            System.out.println();
            recipejson.put("recipeIngredient",jsonArray);


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return sortjson(recipejson);
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
