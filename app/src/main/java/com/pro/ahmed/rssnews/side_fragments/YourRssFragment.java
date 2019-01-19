package com.pro.ahmed.rssnews.side_fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pro.ahmed.rssnews.R;
import com.pro.ahmed.rssnews.adapters.RssAdapter;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;
import com.pro.ahmed.rssnews.viewmodels.ItemsViewModel;
import com.pro.ahmed.rssnews.viewmodels.RssSourcesViewModel;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class YourRssFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvRss)
    RecyclerView rvRss;
    @BindView(R.id.RssInsertContainer)
    LinearLayout rssInsertContainer;
    @BindView(R.id.etRssName)
    EditText etRssName;
    @BindView(R.id.etRssUrl)
    EditText etRssUrl;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private RssSourcesViewModel rssViewModel;
    private ItemsViewModel newsViewModel;
    private RssAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_your_rss, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        getActivity().setTitle("Providers");
        rssViewModel = ViewModelProviders.of(this).get(RssSourcesViewModel.class);
        newsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        adapter = new RssAdapter();
        rvRss.setAdapter(adapter);
        rvRss.setLayoutManager(new LinearLayoutManager(getActivity()));

        //get All Rss from Db
        getRssFromDB();

        //Update Checkable  Rss Source
        adapter.setOnItemClickListener(new RssAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RssSourcesModel rssModel, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), rssModel.getRssName() + "Is Checked", Toast.LENGTH_SHORT).show();
                    rssModel.setChecked(true);
                    rssViewModel.updateRssSource(rssModel);
                    newsViewModel.insertNewItem(rssModel);

                } else {
                    Toast.makeText(getContext(), rssModel.getRssName() + "Is UnChecked", Toast.LENGTH_SHORT).show();
                    rssModel.setChecked(false);
                    rssViewModel.updateRssSource(rssModel);
                    newsViewModel.deleteItems(rssModel.getId()); // delete all news for this RSS
                }
            }
        });

        // Add new Source
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "cecee", Toast.LENGTH_SHORT).show();
                rssInsertContainer.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
            }
        });

        btnSubmit.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(etRssName.getText().toString()) && !TextUtils.isEmpty(etRssUrl.getText().toString())) {
                RssSourcesModel rssSourcesModel = new RssSourcesModel(etRssName.getText().toString(), etRssUrl.getText().toString(), true);
                rssViewModel.insertRssSource(rssSourcesModel).observe(this, new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long rssId) {
                        if (rssId > -1) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            rssSourcesModel.setId(rssId);
                            newsViewModel.insertNewItem(rssSourcesModel);
                            rssInsertContainer.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getActivity(), "Cannot download this RSS feed, make sure the Rss URL is correct. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                etRssName.setError("Please inter this Field");
                etRssUrl.setError("Please inter this Field");
                Toast.makeText(getContext(), "Please inter Fields", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void getRssFromDB() {
        rssViewModel.getAllRssLiveData().observe(this, new Observer<List<RssSourcesModel>>() {
            @Override
            public void onChanged(@Nullable List<RssSourcesModel> rssSourcesModels) {
                Collections.reverse(rssSourcesModels);
                adapter.setRss(rssSourcesModels);

            }
        });
    }

}
