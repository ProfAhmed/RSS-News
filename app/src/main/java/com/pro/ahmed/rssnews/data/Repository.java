package com.pro.ahmed.rssnews.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.pro.ahmed.rssnews.data.api.ApiUtils;
import com.pro.ahmed.rssnews.data.api.IrApiService;
import com.pro.ahmed.rssnews.data.database.IrNewsDao;
import com.pro.ahmed.rssnews.data.database.IrRssSourcesDao;
import com.pro.ahmed.rssnews.data.database.NewsDatabase;
import com.pro.ahmed.rssnews.data.models.ApiResponseModel;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class Repository {

    private IrNewsDao newsDao;
    private IrRssSourcesDao rssSourcesDao;
    private static Repository repository;
    private NewsDatabase db;
    private IrApiService mService;
    private Executor executor = Executors.newSingleThreadExecutor();

    public Repository() {
        db = NewsDatabase.getDatabase();
        newsDao = db.newsDao();
        rssSourcesDao = db.rssSourcesDao();
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    //get All RssSources From Rss Table in Room Db
    public List<RssSourcesModel> getAllRssSources() {
        return db.rssSourcesDao().getAllRss();
    }

    //get All News From News Table in Room Db
    public LiveData<List<ItemModel>> getAllNews() {
//        refreshNews(sources, page);
        return db.newsDao().getAllNews();
    }

    // get All News From Api
    public void refreshNews(List<RssSourcesModel> sources) {
        mService = ApiUtils.getAPIService();
        executor.execute(() -> {
            try {
                for (int k = 0; k < sources.size(); k++) {
                    Response<ApiResponseModel> response = mService.getAllNews(sources.get(k).getRssUrl()).execute();
                    if (response.body().getStatus().equals("ok")) {
                        for (int i = 0; i < response.body().getItems().size(); i++) {
                            ItemModel newsModel = response.body().getItems().get(i);
                            db.newsDao().insert(newsModel);
                        }
                        Log.v("ResponseIs", response.body().feed + " " + k);
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("ResponseIs", e.getMessage());
            }

        });
    }


    public LiveData<Boolean> insertRssSource(RssSourcesModel rssSource) {
        mService = ApiUtils.getAPIService();
        MutableLiveData<Boolean> success = new MutableLiveData<>();
        executor.execute(() -> {
            Response<ApiResponseModel> response;
            try {
                response = mService.getAllNews(rssSource.getRssUrl()).execute();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("ok")) {
                        db.rssSourcesDao().insert(rssSource);
                        success.postValue(true);

                    } else {
                        success.postValue(false);
                    }
                } else {
                    success.postValue(false);
                }
            } catch (IOException e) {
                Log.v("ResponseIsS", e.getMessage());
            }

        });
        return success;
    }

    public void deleteRssSource(RssSourcesModel sources) {
        executor.execute(() -> {
            rssSourcesDao.delete(sources);
        });
    }

}
