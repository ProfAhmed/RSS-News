package com.pro.ahmed.rssnews.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.pro.ahmed.rssnews.data.models.RssSourcesModel;
import com.pro.ahmed.rssnews.helper.SyncServiceSupportImpl;
import com.pro.ahmed.rssnews.viewmodels.ItemsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FetchNewsService extends Service {
    public static final int notify = 10 * 60 * 1000;  //interval between two services(Here Service run every 10 Minute)
    private static ItemsViewModel newsViewModel;
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    private static List<RssSourcesModel> rssSourcesModelList = new ArrayList<>();
    private static SyncServiceSupportImpl iSyncService;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iSyncService = new SyncServiceSupportImpl();

        Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
        newsViewModel = new ItemsViewModel();
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getRssFromDB();
                }
            });
        }
    }

    private static void getRssFromDB() {

        new AsyncTask<Void, Void, List<RssSourcesModel>>() {

            @Override
            protected List<RssSourcesModel> doInBackground(Void... aVoid) {
                rssSourcesModelList = iSyncService.getRssSources();
                return rssSourcesModelList;
            }

            @Override
            protected void onPostExecute(List<RssSourcesModel> rssSourcesModels) {

                Log.v("RssService", rssSourcesModels.toString());
                newsViewModel.refreshNews(rssSourcesModels);
            }
        }.execute();
    }
}
