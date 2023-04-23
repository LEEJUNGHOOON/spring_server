package com.security.springsecuritytest.web;

import com.google.gson.Gson;
import com.security.springsecuritytest.domain.userInfoDetail.UserDetailRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/getintent")
public class JsontransferController {//flask로 부터 intent 구분을 불러오는 controller

    @Autowired
    private UserDetailRepository userDetailRepository;

    @ResponseBody
    @PostMapping("")
    public String flaskspring(@RequestBody String jsonString, HttpServletResponse response) throws ParseException, IOException {

        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsonString);

        System.out.println("from android:" + json);
        //////flask에 json 보내고 intent받는 과정

        InputStream in = null;
        BufferedReader reader = null;
        String result = "";
        String line = null;
        String flask_url = "http://8b5a-34-82-150-197.ngrok.io/chat_request";//flask 서버 URL

        URL url = new URL(flask_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoInput(true);
        con.setUseCaches(false);
        con.setReadTimeout(10000);
        con.setConnectTimeout(10000);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true); //OutputStream 을 사용해서 post body 데이터 전송

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        con.connect();

        int responseCode = con.getResponseCode();
        System.out.println("http"+"response_code : "+responseCode);
        System.out.println("http"+"response : "+con.getResponseMessage());

        con.setInstanceFollowRedirects(true);

        if(responseCode == HttpsURLConnection.HTTP_OK){

            in = con.getInputStream();

        }else{
            in = con.getErrorStream();
        }

        reader = new BufferedReader(new InputStreamReader(in));
        while((line = reader.readLine())!=null){
            result += line;
        }

        reader.close();

        if(con !=null){
            con.disconnect();
        }

        return result;
    }
}
