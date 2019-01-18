package com.pro.ahmed.rssnews.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.pro.ahmed.rssnews.data.Repository;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.util.List;

public class RssSourcesViewModel extends ViewModel {

    private final String TAG = this.getClass().getName();
    private List<RssSourcesModel> listRssSources;

    public List<RssSourcesModel> getListRssSources() {
        if (listRssSources == null) {
            Log.e(TAG, "_ListItemsIsNULL");
            //listRssSources = new MutableLiveData<List<RssSourcesModel>>();
            loadItemsFromRepository();
        }
        Log.e(TAG, "_ReturningFromViewModel");
        return listRssSources;
    }

    public LiveData<Boolean> insertRssSource(RssSourcesModel sourcesModel) {
        return Repository.getInstance().insertRssSource(sourcesModel);
    }

    private void loadItemsFromRepository() {
        Log.e(TAG, "_LoadFromDB");
        listRssSources = Repository.getInstance().getAllRssSources();
    }

    public void deleteRssSource(RssSourcesModel sources) {
        Repository.getInstance().deleteRssSource(sources);
    }

    public void updateRssSource(RssSourcesModel sources) {
        Repository.getInstance().updateRssSource(sources);
    }
}
