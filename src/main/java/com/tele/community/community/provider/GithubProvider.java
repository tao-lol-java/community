package com.tele.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.tele.community.community.dto.AccessTokenDTO;
import com.tele.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO dto) {
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
                System.out.println(token);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
    }


    public GithubUser githubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            /*System.out.println(string.toString());*/
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//将string字符串的内容自动转换成类对象
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
