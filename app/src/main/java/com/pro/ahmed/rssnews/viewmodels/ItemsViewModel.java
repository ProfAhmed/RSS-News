package com.pro.ahmed.rssnews.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.pro.ahmed.rssnews.data.Repository;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.util.List;

public class ItemsViewModel extends ViewModel {
    private final String TAG = this.getClass().getName();
    private LiveData<List<ItemModel>> listNews;

    public LiveData<List<ItemModel>> getListNews() {
        return Repository.getInstance().getAllNews();
    }


    public void refreshNews(List<RssSourcesModel> sources) {
        Repository.getInstance().refreshNews(sources);
    }

    public void deleteItems(long rssId) {
        Repository.getInstance().deleteItems(rssId);
    }

    public void insertNewItem(RssSourcesModel sourcesModel) {
        Repository.getInstance().insertNewItem(sourcesModel);
    }

}
