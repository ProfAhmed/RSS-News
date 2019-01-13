package com.pro.ahmed.rssnews.data.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "RssSources")
public class RssSourcesModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String rssName;

    @NonNull
    private String rssUrl;


    public RssSourcesModel(@NonNull String rssName, @NonNull String rssUrl) {
        this.rssName = rssName;
        this.rssUrl = rssUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getRssName() {
        return rssName;
    }

    public void setRssName(@NonNull String rssName) {
        this.rssName = rssName;
    }

    @NonNull
    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(@NonNull String rssUrl) {
        this.rssUrl = rssUrl;
    }

    @Override
    public String toString() {
        return "RssSourcesModel{" +
                "rssName='" + rssName + '\'' +
                ", rssUrl='" + rssUrl + '\'' +
                ", id=" + id +
                '}';
    }
}
