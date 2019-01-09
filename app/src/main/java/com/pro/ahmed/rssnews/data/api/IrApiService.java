package com.pro.ahmed.rssnews.data.api;

import com.pro.ahmed.rssnews.data.models.ApiResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IrApiService {
    @GET("top-headlines?apiKey=355ba2159b7c467fb0b4ece42f10fc7e")
    Call<ApiResponseModel> getAllNews(@Query("sources") String sources);

}
