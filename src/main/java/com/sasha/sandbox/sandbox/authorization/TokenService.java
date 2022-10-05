package com.sasha.sandbox.sandbox.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasha.sandbox.sandbox.entity.AuthorizationInfo;
import com.sasha.sandbox.sandbox.interceptors.BasicAuthInterceptor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Component
@EnableScheduling
public class TokenService {
    private final String url = "http://localhost:9999/services/ua/oauth/token";

    @Autowired
    ObjectMapper objectMapper;
    BasicAuthInterceptor basicAuthInterceptor;
    private AuthorizationInfo authorizationInfo;

    @Autowired
    public TokenService(BasicAuthInterceptor basicAuthInterceptor) {
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void refreshTokenInfo() {
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
                .url(url)
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            ResponseBody responseBody = client.newCall(request).execute().body();
            AuthorizationInfo newAuthorizationInfo = objectMapper.readValue(responseBody.string(), AuthorizationInfo.class);
            authorizationInfo.addAccessTokenToMapOfOldTokens(new Date(), authorizationInfo.getAccess_token());
            authorizationInfo.setAccess_token(newAuthorizationInfo.getAccess_token());
            System.out.println(authorizationInfo.getOldAccessTokens());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateTokenInfo() {
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(basicAuthInterceptor)
                .build();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .add("grant_type", "password")
                .add("scope", "openid")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            ResponseBody responseBody = client.newCall(request).execute().body();
            authorizationInfo = objectMapper.readValue(responseBody.string(), AuthorizationInfo.class);
            System.out.println(authorizationInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
