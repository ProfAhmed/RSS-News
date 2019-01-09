package com.pro.ahmed.rssnews.data.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "RssSources")
public class RssSourcesModel {

    @NonNull
    private String rssName;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public RssSourcesModel(String rssName) {
    this.rssName = rssName;
    }

    @NonNull
    public String getRssName() {
        return rssName;
    }

    public void setRssName(@NonNull String rssName) {
        this.rssName = rssName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
