package com.pro.ahmed.rssnews.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.pro.ahmed.rssnews.data.Repository;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private final String TAG = this.getClass().getName();
    private LiveData<List<ItemModel>> listNews;

    public LiveData<List<ItemModel>> getListNews() {
        if (listNews == null) {
            Log.e(TAG, "_ListNewsIsNULL");
            listNews = new MutableLiveData<List<ItemModel>>();
            loadItemsFromRepository();
        }
        Log.e(TAG, "_ReturningFromViewModel");
        return listNews;
    }

    private void loadItemsFromRepository() {
        Log.e(TAG, "_LoadFromDB");
        listNews = Repository.getInstance().getAllNews();
    }

    public void refreshNews(List<RssSourcesModel> sources) {
        Repository.getInstance().refreshNews(sources);
    }
}
