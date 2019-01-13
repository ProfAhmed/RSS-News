package com.pro.ahmed.rssnews;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;
import com.pro.ahmed.rssnews.services.FetchNewsService;
import com.pro.ahmed.rssnews.viewmodels.NewsViewModel;
import com.pro.ahmed.rssnews.viewmodels.RssSourcesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static RssSourcesViewModel rssSourcesViewModel;
    NewsViewModel newsViewModel;
    TextView textView;
    private static RssSourcesModel rssSourcesModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.t);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        newsViewModel = ViewModelProviders.of(MainActivity.this).get(NewsViewModel.class);
        rssSourcesViewModel = ViewModelProviders.of(this).get(RssSourcesViewModel.class);

        newsViewModel.getListNews().observe(MainActivity.this, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(@Nullable List<ItemModel> newsModels) {
                for (int i = 0; i < newsModels.size(); i++) {
                    Log.v("RssSourceNews", newsModels.get(i).toString() + " (" + i + ")");
                    textView.setMovementMethod(new ScrollingMovementMethod());
                    textView.append(newsModels.get(i).toString() + " (" + i + ")");
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "AAAa", Toast.LENGTH_SHORT).show();

                rssSourcesModel = new RssSourcesModel("ycombinator", "https://news.ycombinator.com/rss");
                Toast.makeText(MainActivity.this, "Rss Id Is  " + rssSourcesModel.getId(), Toast.LENGTH_SHORT).show();

                rssSourcesViewModel.insertRssSource(rssSourcesModel).observe(MainActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            Executors.newSingleThreadExecutor().execute(() -> {
//                                stopService(new Intent(MainActivity.this, FetchNewsService.class));
//                            });
//                            startService(new Intent(MainActivity.this, FetchNewsService.class)); //start service which is MyService.java
                            refreshService();
                        } else {
                            Toast.makeText(MainActivity.this, "Cannot download this RSS feed, make sure the Rss URL is correct. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rssSourcesModel != null) {

                    delete();
                    refreshService();
                }
            }
        });
        startService(new Intent(this, FetchNewsService.class)); //start service which is MyService.java
    }

    void refreshService() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                stopService(new Intent(MainActivity.this, FetchNewsService.class));
                Log.v("ServiceInback", "Load");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startService(new Intent(MainActivity.this, FetchNewsService.class)); //start service which is MyService.java
                Toast.makeText(MainActivity.this, "Service Is Start", Toast.LENGTH_SHORT).show();
            }
        }.execute();

    }

    void delete() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rssSourcesModel.setId(1);
                rssSourcesViewModel.deleteRssSource(rssSourcesModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this, "Rss Id Is  " + rssSourcesModel.getId(), Toast.LENGTH_SHORT).show();
                Log.v("RssService", "Rss Id Is  " + rssSourcesModel.getId());

            }
        }.execute();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_add_rss) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
