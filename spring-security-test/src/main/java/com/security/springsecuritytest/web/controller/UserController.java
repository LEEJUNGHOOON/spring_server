package com.security.springsecuritytest.web.controller;

import com.security.springsecuritytest.domain.user.FireBaseUser;
import com.security.springsecuritytest.domain.userInfoDetail.FireBaseUserDetail;
import com.security.springsecuritytest.service.FireBaseUserService;
import com.security.springsecuritytest.web.dto.FireBaseUserDetailDto;
import com.security.springsecuritytest.web.dto.FireBaseUserDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
@RequestMapping("/android")
public class UserController {
    @Autowired
    private final FireBaseUserService fireBaseUserService;

    @ResponseBody
    @PostMapping("/saveNewUser")
    public String saveNewUser(@RequestBody String jsondata) throws ParseException {
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
    @PostMapping("/getUserDetail")
    public String getUser(@RequestParam("UID") String UID) throws ParseException{
        System.out.println("getUserDetail On");

        FireBaseUserDetail result = fireBaseUserService.getFireBaseUserDetail(UID);

        if(result == null){
            return "-1";
        }else {
            JSONObject user = new JSONObject();

            user.put("UID",result.getUID());
            user.put("lastfood",result.getLastfood());
            user.put("foodtaste",result.getFoodtaste());

            return user.toJSONString();
        }

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
