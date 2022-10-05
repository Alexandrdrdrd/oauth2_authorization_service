package com.sasha.sandbox.sandbox.interceptors;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("BasicAuthInterceptor")
@PropertySource("classpath:application.properties")
public class BasicAuthInterceptor implements Interceptor {

    private String credentials;

    @Autowired
    public BasicAuthInterceptor(@Value("${client_id}") String client_id, @Value("${secret}") String secret) {
        this.credentials = Credentials.basic(client_id, secret);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}
