package com.pro.ahmed.rssnews;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(itemModel.getLink()));
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.item_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            Toast.makeText(this, "AAA", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
}
