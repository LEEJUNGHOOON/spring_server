package com.security.springsecuritytest.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@Controller
@RequestMapping("/getintent")
public class JsontransferController {//flask로 부터 intent 구분을 불러오는 controller
    @ResponseBody
    @PostMapping("")
    public String flaskspring(@RequestBody String jsonString, HttpServletResponse response) throws ParseException, IOException {

        JSONParser jsonParser = new JSONParser();

        JSONObject json=new JSONObject();
        json = (JSONObject)jsonParser.parse(jsonString);//json parser를 이용하여 RequestBody에서 String 으로 넘어온 문자열을 json형태로 바꿔줌

        System.out.println("from android:" + json);
        //////flask에 json 보내고 intent받는 과정

        InputStream in = null;
        BufferedReader reader = null;
        String result = "";
        String line = null;
        String flask_url = "http://8b5a-34-82-150-197.ngrok.io/chat_request";//flask 서버 URL

        URL url = new URL(flask_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();//연결

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
        System.out.println("http"+"response_code : "+responseCode);//flask 서버에서 받은 응답코드
        System.out.println("http"+"response : "+con.getResponseMessage());//flask 서버에서 받은 응답메세지

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

        return result;//flask서버로 부터 받은 응답을 안드로이드 어플리케이션으로 리턴
    }
}
