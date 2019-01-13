package com.pro.ahmed.rssnews.data.models;

import java.util.List;

public class ApiResponseModel {

    public String status;
    public FeedModel feed;
    public List<ItemModel> items = null;
    public String message;

    public ApiResponseModel() {
    }

    public ApiResponseModel(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FeedModel getFeed() {
        return feed;
    }

    public void setFeed(FeedModel feed) {
        this.feed = feed;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
