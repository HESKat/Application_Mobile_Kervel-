package com.example.kervel.controleur;

import com.example.kervel.controleur.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//implementation de linterface Api
//client rest est la ibliotheque retrofit utilisé coté client (android)
//
public class RetrofitClient {
    public static final String BASE_URL = "http://192.168.43.189";

    private Api api;
    public Api getService(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(200, TimeUnit.SECONDS)
                .connectTimeout(200, TimeUnit.SECONDS)
                .build();
        if (api==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(Api.class);
        }
        return api;


    }
}