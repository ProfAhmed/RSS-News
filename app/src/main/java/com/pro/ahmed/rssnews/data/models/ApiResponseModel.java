package com.pro.ahmed.rssnews.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private List<NewsModel> articles = null;

    public ApiResponseModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsModel> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsModel> articles) {
        this.articles = articles;
    }
}
