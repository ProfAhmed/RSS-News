package com.pro.ahmed.rssnews.viewmodels;

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

    private void loadItemsFromRepository() {
        Log.e(TAG, "_LoadFromDB");
        listRssSources = Repository.getInstance().getAllRssSources();
    }
}
