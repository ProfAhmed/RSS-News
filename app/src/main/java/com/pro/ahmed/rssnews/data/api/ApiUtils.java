package com.pro.ahmed.rssnews.data.api;

import static com.pro.ahmed.rssnews.utils.Constants.BASE_URL;

public class ApiUtils {
    private ApiUtils() {
    }


    public static IrApiService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(IrApiService.class);
    }
}
