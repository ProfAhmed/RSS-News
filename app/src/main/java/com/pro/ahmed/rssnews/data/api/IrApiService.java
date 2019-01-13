package com.pro.ahmed.rssnews.data.api;

import com.pro.ahmed.rssnews.data.models.ApiResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IrApiService {
    @GET("api.json?")
    Call<ApiResponseModel> getAllNews(@Query("rss_url") String rssUrl);
}
