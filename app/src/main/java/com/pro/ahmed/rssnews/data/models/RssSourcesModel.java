package com.pro.ahmed.rssnews.data.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "RssSources")
public class RssSourcesModel {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String rssName;

    @NonNull
    private String rssUrl;

    private Boolean isChecked;

    public RssSourcesModel(@NonNull String rssName, @NonNull String rssUrl, Boolean isChecked) {
        this.rssName = rssName;
        this.rssUrl = rssUrl;
        this.isChecked = isChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "RssSourcesModel{" +
                "id=" + id +
                ", rssName='" + rssName + '\'' +
                ", rssUrl='" + rssUrl + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
