package com.pro.ahmed.rssnews.side_fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pro.ahmed.rssnews.ItemDetailsActivity;
import com.pro.ahmed.rssnews.R;
import com.pro.ahmed.rssnews.adapters.NewsAdapter;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.services.FetchNewsService;
import com.pro.ahmed.rssnews.viewmodels.ItemsViewModel;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {

    @BindView(R.id.rvItems)
    RecyclerView rvItem;

    private ItemsViewModel newsViewModel;
    public static ItemModel mItemModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        getActivity().setTitle("Home");
        newsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        final NewsAdapter adapter = new NewsAdapter(getActivity());
        rvItem.setAdapter(adapter);
        rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsViewModel.getListNews().observe(getActivity(), itemModels -> {
            Log.v("ItemSize", String.valueOf(itemModels.size()));
            Collections.reverse(itemModels);
            adapter.setItems(itemModels);
        });

        adapter.setOnItemClickListener(itemModel -> {
            mItemModel = itemModel;
            startActivity(new Intent(getActivity(), ItemDetailsActivity.class));
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refreshServic();
        }
        return super.onOptionsItemSelected(item);

    }

    private void refreshServic() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                getActivity().stopService(new Intent(getActivity(), FetchNewsService.class));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getActivity().startService(new Intent(getActivity(), FetchNewsService.class));

            }
        }.execute();
    }
}
