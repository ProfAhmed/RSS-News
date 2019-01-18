package com.pro.ahmed.rssnews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.ahmed.rssnews.R;
import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemsViewHolder> {
    private OnItemClickListener listener;
    List<ItemModel> mItemModelList;
    Context mContext;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_news_list, viewGroup, false);
        ItemsViewHolder viewHolder = new ItemsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i) {
        ItemModel itemModel = mItemModelList.get(i);
        itemsViewHolder.tvTitle.setText(itemModel.getTitle());
        try {
            Picasso.get().load(itemModel.getThumbnail()).into(itemsViewHolder.ivImage);

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        if (mItemModelList != null) {
            return mItemModelList.size();
        } else {
            return 0;
        }
    }

    public void setItems(List<ItemModel> itemModels) {
        mItemModelList = itemModels;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class ItemsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(mItemModelList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ItemModel itemModel);
    }
}
