package com.pro.ahmed.rssnews.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.pro.ahmed.rssnews.data.Repository;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.util.List;

public class RssSourcesViewModel extends ViewModel {

    private final String TAG = this.getClass().getName();

    public List<RssSourcesModel> getListRssSources() {
        return Repository.getInstance().getAllRssSources();
    }

    public LiveData<Long> insertRssSource(RssSourcesModel sourcesModel) {
        return Repository.getInstance().insertRssSource(sourcesModel);
    }


    public void deleteRssSource(RssSourcesModel sources) {
        Repository.getInstance().deleteRssSource(sources);
    }

    public void updateRssSource(RssSourcesModel sources) {
        Repository.getInstance().updateRssSource(sources);
    }

    public LiveData<List<RssSourcesModel>> getAllRssLiveData() {
        return Repository.getInstance().getAllRssSourcesLiveData();
    }
}
