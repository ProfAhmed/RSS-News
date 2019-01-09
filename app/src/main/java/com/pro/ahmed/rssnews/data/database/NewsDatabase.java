package com.pro.ahmed.rssnews.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.pro.ahmed.rssnews.DataProcessor;
import com.pro.ahmed.rssnews.MyApplication;
import com.pro.ahmed.rssnews.data.models.NewsModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import static com.pro.ahmed.rssnews.Constants.FIRST_RUN;


@Database(entities = {NewsModel.class, RssSourcesModel.class}, version = 1)

public abstract class NewsDatabase extends RoomDatabase {

    public abstract IrNewsDao newsDao();

    public abstract IrRssSourcesDao rssSourcesDao();

    private static volatile NewsDatabase INSTANCE;

    public synchronized static NewsDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MyApplication.getAppContext(),
                            NewsDatabase.class, "news_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                    //just run in first time
                    if (DataProcessor.getInstance(MyApplication.getAppContext()).getBool(FIRST_RUN)) {
                        DataProcessor.getInstance(MyApplication.getAppContext()).setBool(FIRST_RUN, false);
                        new PopulateDbAsync(INSTANCE).execute();
                    }
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final IrRssSourcesDao mDao;

        PopulateDbAsync(NewsDatabase db) {
            mDao = db.rssSourcesDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            RssSourcesModel rssSourcesModel0 = new RssSourcesModel("TechCrunch");
            mDao.insert(rssSourcesModel0);

            RssSourcesModel rssSourcesModel1 = new RssSourcesModel("Mashable");
            mDao.insert(rssSourcesModel1);

            RssSourcesModel rssSourcesModel2 = new RssSourcesModel("Business-insider");
            mDao.insert(rssSourcesModel2);

            return null;
        }
    }
}
