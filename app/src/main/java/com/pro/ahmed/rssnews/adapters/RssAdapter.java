package com.pro.ahmed.rssnews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pro.ahmed.rssnews.R;
import com.pro.ahmed.rssnews.data.models.RssSourcesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {
    private OnItemClickListener listener;
    List<RssSourcesModel> mRssModelList;

    public RssAdapter() {

    }


    @NonNull
    @Override
    public RssViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_rss_list, viewGroup, false);
        RssViewHolder viewHolder = new RssViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RssViewHolder rssViewHolder, int i) {
        RssSourcesModel rssModel = mRssModelList.get(i);
        rssViewHolder.tvRssName.setText(rssModel.getRssName());
        rssViewHolder.cbBox.setChecked(rssModel.isChecked());
    }

    @Override
    public int getItemCount() {
        if (mRssModelList != null) {
            return mRssModelList.size();
        } else {
            return 0;
        }
    }

    public void setRss(List<RssSourcesModel> rssModels) {
        mRssModelList = rssModels;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RssAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public class RssViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRssName)
        TextView tvRssName;
        @BindView(R.id.cbRss)
        CheckBox cbBox;

        public RssViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cbBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = cbBox.isChecked();
                    int position = getAdapterPosition();
                    if (isChecked) {
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(mRssModelList.get(position), isChecked);
                        }
                    } else {
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(mRssModelList.get(position), isChecked);
                        }
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(RssSourcesModel rssModel, boolean isChecked);
    }
}
