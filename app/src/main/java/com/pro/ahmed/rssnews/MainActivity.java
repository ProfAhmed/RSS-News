package com.pro.ahmed.rssnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.ahmed.rssnews.data.models.RssSourcesModel;
import com.pro.ahmed.rssnews.services.FetchNewsService;
import com.pro.ahmed.rssnews.side_fragments.HomeFragment;
import com.pro.ahmed.rssnews.side_fragments.YourRssFragment;
import com.pro.ahmed.rssnews.viewmodels.NewsViewModel;
import com.pro.ahmed.rssnews.viewmodels.RssSourcesViewModel;

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

        //textView = (TextView) findViewById(R.id.t);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment()).commit();
//        rssSourcesViewModel = ViewModelProviders.of(this).get(RssSourcesViewModel.class);

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this, "AAAa", Toast.LENGTH_SHORT).show();
//
//                rssSourcesModel = new RssSourcesModel("ycombinator", "https://news.ycombinator.com/rss");
//                Toast.makeText(MainActivity.this, "Rss Id Is  " + rssSourcesModel.getId(), Toast.LENGTH_SHORT).show();
//
//                rssSourcesViewModel.insertRssSource(rssSourcesModel).observe(MainActivity.this, new Observer<Boolean>() {
//                    @Override
//                    public void onChanged(@Nullable Boolean aBoolean) {
//                        if (aBoolean) {
//                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            refreshService();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Cannot download this RSS feed, make sure the Rss URL is correct. ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (rssSourcesModel != null) {
//
//                    delete();
//                    refreshService();
//                }
//            }
//        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new HomeFragment()).commit();

        } else if (id == R.id.nav_your_rss) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new YourRssFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, FetchNewsService.class)); //start service which is MyService.java
    }
}
