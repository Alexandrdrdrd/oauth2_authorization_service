package com.sasha.sandbox.sandbox.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasha.sandbox.sandbox.entity.AuthorizationInfo;
import com.sasha.sandbox.sandbox.interceptors.BasicAuthInterceptor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

public class TokenService {

    BasicAuthInterceptor basicAuthInterceptor;

    AuthorizationInfo authorizationInfo;

    public TokenService(@Qualifier("BasicAuthInterceptor") BasicAuthInterceptor basicAuthInterceptor) {
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    public void refreshToken() {

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(basicAuthInterceptor)
                .build();
        RequestBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .add("grant_type", "refresh_token")
                .add("scope", "openid")
                .add("refresh_token", authorizationInfo.getRefresh_token())


                .build();

        Request request = new Request.Builder()
                .url("http://localhost:9999/services/ua/oauth/token")
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAccessToken() {
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(basicAuthInterceptor)
                .build();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .add("grant_type", "password")
                .add("scope", "openid")


                .build();

        Request request = new Request.Builder()
                .url("http://localhost:9999/services/ua/oauth/token")
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            ResponseBody responseBody = client.newCall(request).execute().body();
            ObjectMapper objectMapper = new ObjectMapper();
            authorizationInfo = objectMapper.readValue(responseBody.string(), AuthorizationInfo.class);
            System.out.println(authorizationInfo.getAccess_token());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
