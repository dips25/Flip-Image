package com.example.flipimage.Api;

import com.example.flipimage.Models.Data;
import com.example.flipimage.Models.GameImage;
import com.example.flipimage.Models.MainData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GameApi {

    @Headers("Keydata:435643653467655")
    @GET("game")
    Call<MainData> getMainData();
    @GET
    Call<Data> getData();
    @GET
    Call<GameImage> getGameImage();
}
