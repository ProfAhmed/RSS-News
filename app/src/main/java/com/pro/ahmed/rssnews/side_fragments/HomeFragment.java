package com.pro.ahmed.rssnews.side_fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pro.ahmed.rssnews.ItemDetailsActivity;
import com.pro.ahmed.rssnews.R;
import com.pro.ahmed.rssnews.adapters.NewsAdapter;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.viewmodels.NewsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {

    @BindView(R.id.rvItems)
    RecyclerView rvItem;

    private NewsViewModel newsViewModel;
    public static ItemModel mItemModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        final NewsAdapter adapter = new NewsAdapter(getActivity());
        rvItem.setAdapter(adapter);
        rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsViewModel.getListNews().observe(getActivity(), itemModels -> {
            Log.v("ItemSize", String.valueOf(itemModels.size()));
            adapter.setItems(itemModels);
        });

        adapter.setOnItemClickListener(itemModel -> {
            Toast.makeText(getActivity(), itemModel.getTitle(), Toast.LENGTH_SHORT).show();
            mItemModel = itemModel;
            startActivity(new Intent(getActivity(), ItemDetailsActivity.class));
        });
        return v;
    }

}
