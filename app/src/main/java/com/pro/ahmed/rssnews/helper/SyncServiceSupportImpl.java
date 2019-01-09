package com.pro.ahmed.rssnews.helper;

import com.pro.ahmed.rssnews.data.models.RssSourcesModel;
import com.pro.ahmed.rssnews.viewmodels.RssSourcesViewModel;

import java.util.List;

public class SyncServiceSupportImpl implements SyncServiceSupport {

    private RssSourcesViewModel viewModel;

    public SyncServiceSupportImpl() {
        viewModel = new RssSourcesViewModel();
    }


    @Override
    public List<RssSourcesModel> getRssSources() {
        return viewModel.getListRssSources();
    }
}