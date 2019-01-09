package com.pro.ahmed.rssnews.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.pro.ahmed.rssnews.data.Repository;
import com.pro.ahmed.rssnews.data.models.NewsModel;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private final String TAG = this.getClass().getName();
    private LiveData<List<NewsModel>> listNews;

    public LiveData<List<NewsModel>> getListNews() {
        if (listNews == null) {
            Log.e(TAG, "_ListNewsIsNULL");
            listNews = new MutableLiveData<List<NewsModel>>();
            loadItemsFromRepository();
        }
        Log.e(TAG, "_ReturningFromViewModel");
        return listNews;
    }

    private void loadItemsFromRepository() {
        Log.e(TAG, "_LoadFromDB");
        listNews = Repository.getInstance().getAllNews();
    }

    public void refreshNews(String sources) {
        Repository.getInstance().refreshNews(sources);
    }
}
