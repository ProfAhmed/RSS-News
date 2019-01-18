package com.pro.ahmed.rssnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.ahmed.rssnews.data.models.ItemModel;
import com.pro.ahmed.rssnews.side_fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.btnOpen)
    Button btnOpen;
    private ItemModel itemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);
        itemModel = HomeFragment.mItemModel;
        setData();
    }

    private void setData() {
        try {
            Picasso.get().load(itemModel.getThumbnail()).into(ivImage);

        } catch (Exception e) {

        }

        tvContent.setText(itemModel.getDescription());
    }

    public void openUrl(View v) {
        Toast.makeText(this, itemModel.getLink(), Toast.LENGTH_SHORT).show();
    }
}
