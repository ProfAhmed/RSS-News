package com.pro.ahmed.rssnews.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.util.List;

@Dao
public interface IrRssSourcesDao {
    @Insert
    void insert(RssSourcesModel rssSourcesModel);

    @Delete
    void delete(RssSourcesModel rssSourcesModel);

    @Query("Select * from RssSources")
    List<RssSourcesModel> getAllRss();

    @Update
    void update(RssSourcesModel rssSourcesModel);
}
