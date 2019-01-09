package com.pro.ahmed.rssnews.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pro.ahmed.rssnews.data.models.NewsModel;

import java.util.List;

@Dao
public interface IrNewsDao {
    @Insert
    void insert(NewsModel newsModel);

    @Query("Select * From News")
    LiveData<List<NewsModel>> getAllNews();
}
