package com.sasha.sandbox.sandbox;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasha.sandbox.sandbox.authorization.TokenService;
import com.sasha.sandbox.sandbox.entity.AuthorizationInfo;
import com.sasha.sandbox.sandbox.interceptors.BasicAuthInterceptor;
import okhttp3.*;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import java.io.IOException;
import java.util.*;


@SpringBootApplication
public class SandboxApplication {
    private String refresh_token;

    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigurableApplicationContext appContext = SpringApplication.run(SandboxApplication.class, args);
        TokenService tokenService = appContext.getBean(TokenService.class);
        tokenService.generateTokenInfo();

        TreeMap<Date,String> tokenList;
        Date date = new Date();
        Thread.sleep(1000);
        Date date1 = new Date();
        System.out.println(date1.getTime() - date.getTime() );
        LinkedHashMap<Integer,Integer> ds;





    }


}