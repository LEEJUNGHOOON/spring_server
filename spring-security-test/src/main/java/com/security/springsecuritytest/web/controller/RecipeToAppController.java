package com.security.springsecuritytest.web.controller;

import com.google.gson.Gson;
import com.security.springsecuritytest.domain.recipeDetail.Recipedetail;
import com.security.springsecuritytest.domain.recipeDetail.RecipedetailRepo;
import com.security.springsecuritytest.domain.recipeIngredient.RecipeIngredientRepo;
import com.security.springsecuritytest.domain.recipeIngredient.Recipeingredient;
import com.security.springsecuritytest.domain.recipeList.RecipeList;
import com.security.springsecuritytest.domain.recipeList.RecipeListRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/android")
public class RecipeToAppController {

    @Autowired
    private final RecipeListRepo recipeListRepo;//전체레시피
    private final RecipeIngredientRepo recipeIngredientRepo;//해당레시피의 재료등
    private final RecipedetailRepo recipedetailRepo;//해당레시피의 조리순서

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

            List<RecipeList> rdList = (List<RecipeList>)recipeListRepo.findAll();//db에서 모든레시피들을 가져옴

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
        //{
        //    "recipelist": [
        //        {
        //            "ID": 0,
        //            "imgsrc": "img1",
        //            "Name": "라면"
        //        },
        //        {
        //            "ID": 1,
        //            "imgsrc": "img2",
        //            "Name": "김치찌개"
        //        }
        //    ]
        //}이렇게 리턴해줌
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
            List<Recipeingredient> rilist = recipeIngredientRepo.findByRecipeLList(Integer.parseInt(id));//jpql쿼리를 날려서 해당하는 id값을 가진 레시피 재료 정보들을 가져옴

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

        return recipejson;//안드로이드 app한테 해당하는 재료의 정보들을 리턴
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
            List<Recipedetail> rdList = recipedetailRepo.findByRecipeLList(Integer.parseInt(id));//jpql을 이용하여 해당하는 id의 레시피 조리순서들을 가져옴

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

        return sortjson(recipejson);//조리 순서대로 정렬된 레시피 조리방법을 리턴해줌
    }

    @ResponseBody
    @GetMapping("/recipeListByTag")//레시피 전체 리스트를 post형식으로 안드로이드에게 보내줌
    public JSONObject recipeListByTag(@RequestParam("tag_string")String tag){
        System.out.println("서버에서 안드로이드 접속 요청함("+tag+")");

        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject recipejson= new JSONObject();


        try{
            System.out.println("Tag:"+ tag);
            List<RecipeList> tagRecipeList = recipeListRepo.findByTag(tag);//jpql을 이용하여 해당하는 id의 레시피 조리순서들을 가져옴

            for (RecipeList recipeList:tagRecipeList) {
                System.out.println(recipeList.getClass());

                Gson gson = new Gson();

                String jsonString = gson.toJson(recipeList);

                JSONObject json= new JSONObject();
                json = (JSONObject)jsonParser.parse(jsonString);
                jsonArray.add(json);
            }
            System.out.println();
            recipejson.put("recipelist",jsonArray);
            System.out.println(jsonArray);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return sortjson(recipejson);//조리 순서대로 정렬된 레시피 조리방법을 리턴해줌
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
