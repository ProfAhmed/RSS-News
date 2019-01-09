package com.pro.ahmed.rssnews.data;

import android.arch.lifecycle.LiveData;

import com.pro.ahmed.rssnews.data.api.ApiUtils;
import com.pro.ahmed.rssnews.data.api.IrApiService;
import com.pro.ahmed.rssnews.data.database.IrNewsDao;
import com.pro.ahmed.rssnews.data.database.IrRssSourcesDao;
import com.pro.ahmed.rssnews.data.database.NewsDatabase;
import com.pro.ahmed.rssnews.data.models.ApiResponseModel;
import com.pro.ahmed.rssnews.data.models.NewsModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

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

    public List<RssSourcesModel> getAllRssSources() {
        return db.rssSourcesDao().getAllRss();
    }

    public LiveData<List<NewsModel>> getAllNews() {
//        refreshNews(sources, page);
        return db.newsDao().getAllNews();
    }

    public void refreshNews(String sources) {
        mService = ApiUtils.getAPIService();
        executor.execute(() -> {
            try {
                Response<ApiResponseModel> response = mService.getAllNews(sources).execute();
                for (int i = 0; i < response.body().getArticles().size(); i++) {
                    NewsModel newsModel = response.body().getArticles().get(i);
                    db.newsDao().insert(newsModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
