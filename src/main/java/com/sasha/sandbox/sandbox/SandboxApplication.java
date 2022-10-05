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


    }


}