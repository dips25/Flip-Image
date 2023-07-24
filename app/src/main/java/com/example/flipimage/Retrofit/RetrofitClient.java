package com.example.flipimage.Retrofit;

import com.example.flipimage.Api.GameApi;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://jbmatrix.in/dev2/screenzyapp/screenzyapp/api/";

    public static Retrofit retrofit;
    public static RetrofitClient retrofitClient;

    private RetrofitClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();


        }


    }

    public static RetrofitClient getInstance() {

        if (retrofitClient == null) {

            return new RetrofitClient();
        }

        return retrofitClient;

    }

    public GameApi getApi(){

        return retrofit.create(GameApi.class);
    }

}

