package com.pro.ahmed.rssnews.side_fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pro.ahmed.rssnews.R;
import com.pro.ahmed.rssnews.adapters.RssAdapter;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;
import com.pro.ahmed.rssnews.helper.SyncServiceSupportImpl;
import com.pro.ahmed.rssnews.services.FetchNewsService;
import com.pro.ahmed.rssnews.viewmodels.NewsViewModel;
import com.pro.ahmed.rssnews.viewmodels.RssSourcesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class YourRssFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvRss)
    RecyclerView rvRss;
    private RssSourcesViewModel rssViewModel;
    private NewsViewModel newsViewModel;
    private RssAdapter adapter;

    private static List<RssSourcesModel> iEntities = new ArrayList<>();
    private static SyncServiceSupportImpl iSyncService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_your_rss, container, false);
        ButterKnife.bind(this, v);
        RssSourcesViewModel rssSourcesViewModel = ViewModelProviders.of(this).get(RssSourcesViewModel.class);
        iSyncService = new SyncServiceSupportImpl();

        rssViewModel = ViewModelProviders.of(this).get(RssSourcesViewModel.class);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        adapter = new RssAdapter(getActivity());
        rvRss.setAdapter(adapter);
        rvRss.setLayoutManager(new LinearLayoutManager(getActivity()));

        getRssFromDB();

        adapter.setOnItemClickListener(new RssAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RssSourcesModel rssModel, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), rssModel.getRssName() + "Is Checked", Toast.LENGTH_SHORT).show();
                    rssModel.setChecked(true);
                    rssViewModel.updateRssSource(rssModel);
                    refreshService();

                } else {
                    Toast.makeText(getContext(), rssModel.getRssName() + "Is UnChecked", Toast.LENGTH_SHORT).show();
                    rssModel.setChecked(false);
                    rssViewModel.updateRssSource(rssModel);
                    newsViewModel.deleteItems(rssModel.getId()); // delete all news for this RSS
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "cecee", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void getRssFromDB() {

        new AsyncTask<Void, Void, List<RssSourcesModel>>() {

            @Override
            protected List<RssSourcesModel> doInBackground(Void... aVoid) {
                iEntities = iSyncService.getRssSources();
                return iEntities;
            }

            @Override
            protected void onPostExecute(List<RssSourcesModel> rssSourcesModels) {

                Log.v("RssService", rssSourcesModels.toString());
                adapter.setRss(rssSourcesModels);

            }
        }.execute();
    }

    void refreshService() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                getActivity().stopService(new Intent(getActivity(), FetchNewsService.class));
                Log.v("ServiceInback", "Load");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getActivity().startService(new Intent(getActivity(), FetchNewsService.class)); //start service which is MyService.java
                Toast.makeText(getActivity(), "Service Is Start", Toast.LENGTH_SHORT).show();
            }
        }.execute();

    }
}
