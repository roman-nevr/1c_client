package com.example.dmitry.a1c_client.data.network.http;

/**
 * Created by Admin on 15.12.2016.
 */

import android.util.Base64;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpMoon {

    public static String administrator = "Администратор";
    public static String password = "1";

    public static List<String> log = new ArrayList<>();

    private static OkHttpClient.Builder getBuilder(){
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true).readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS).followRedirects(true)
                .connectTimeout(1000, TimeUnit.MILLISECONDS);
    }

    public static OkHttpClient.Builder getClientBuilder(String login, String password){
        return getBuilder()
                .addInterceptor(new AuthInterceptor(login, password));
    }

    private static class AuthInterceptor implements Interceptor {
        private boolean retry = true;
        private String username, password;

        public AuthInterceptor(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            String credentials = username + ":" + password;
            String credential = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Request request = chain.request().newBuilder()
                    .header("Authorization", credential).build();
            Response response;
            if(retry){
                try {
                    response = chain.proceed(request);
                }catch (SocketTimeoutException e){
                    response = chain.proceed(request);
                }
            }else{
                response = chain.proceed(request);
                retry = true;
            }
            return response;
        }
    }


}