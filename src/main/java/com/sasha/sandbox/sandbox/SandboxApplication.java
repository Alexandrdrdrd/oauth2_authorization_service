package com.sasha.sandbox.sandbox;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasha.sandbox.sandbox.entity.AuthorizationInfo;
import com.sasha.sandbox.sandbox.interceptors.BasicAuthInterceptor;
import okhttp3.*;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;




@SpringBootApplication
public class SandboxApplication {
    private String refresh_token;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SandboxApplication.class, args);
//SandboxApplication.refreshToken();
SandboxApplication.getAccessToken();

    }
    public static void refreshToken(){
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new BasicAuthInterceptor("a", "a"))
                .build();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .add("grant_type", "refresh_token")
                .add("scope", "openid")
                .add("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJhdGkiOiI2ZmM2OTE2Mi0wMTFmLTRjNzUtODFhOS1hNTUyODA4NDk2NDMiLCJleHAiOjE2NjU1NjQ5MDAsImlhdCI6MTY2NDk2MDEwMCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJkMjVlMTIzMi03ZDg1LTQ2MzUtYTgxOS0xYzQxY2UzMjdiYzYiLCJjbGllbnRfaWQiOiJhIn0.itbsZ5J20cAgzKLiIeEWIfWEt6lKrUgCCYqB1dOQ1dmkfnU8yPGDVBulm9hWtsxNmaZmRl5DKtfy8L9Cw2Vp7p5T6K9Im0cuiXJT9Rp9o_uXvlK2TcB5Ssgczu747RP8sZlF48uktKNzPTJ9xOepTSADjNjsgqBsKuv7bzCi4q6J6JHiJwc3ekb-a6yZTCh8cDjl1PB0akDn34-StmH962OFt7_6TzEbheRLKzNrGUOJzDhFgNOU1ltlXlGAw0C1P0bCtxzoCOhUMuGrkfkNbTYD4u2Sljp75Jm4MqskpvQo3qcWmmsp_4EBvLLl3TRoZyEGypfBVNwhW39fIwaoDw")


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
public static void getAccessToken(){
    OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new BasicAuthInterceptor("a", "a"))
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
        AuthorizationInfo authorizationInfo = objectMapper.readValue(responseBody.string(), AuthorizationInfo.class);
        System.out.println(authorizationInfo.getAccess_token());

    } catch (IOException  e) {
        e.printStackTrace();
    }
}
}