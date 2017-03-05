package cn.com.nggirl.ngdemo.pullzoom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dinuscxj.pullzoom.PullZoomRecyclerView;

import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.model.DataServer;

/**
 * 下拉放大
 */
public class PullZoomRecyclerViewActivity extends AppCompatActivity {
    private PullZoomRecyclerView mRecyclerView;

    public static Intent newInstance(Context context) {
        return new Intent(context, PullZoomRecyclerViewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_zoom);
        mRecyclerView = (PullZoomRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new AnimationAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public class AnimationAdapter extends BaseMultiItemQuickAdapter<MultiStatus, BaseViewHolder> {
        AnimationAdapter() {
            super(DataServer.getSampleMultiData(100));
            addItemType(MultiStatus.ITEM_HEADER, R.layout.include_pull_zoom_header);
            addItemType(MultiStatus.ITEM_ITEM, R.layout.layout_animation);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiStatus item) {
            switch (item.getItemType()) {
                case MultiStatus.ITEM_HEADER:
                    ImageView mZoomImageView = helper.getView(R.id.zoom_image_view);
                    ViewGroup mZoomHeaderContainer = helper.getView(R.id.zoom_header_container);
                    mRecyclerView.setZoomView(mZoomImageView);
                    mRecyclerView.setHeaderContainer(mZoomHeaderContainer);
                    break;
                case MultiStatus.ITEM_ITEM:
                    helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetText).addOnClickListener(R.id.tweetName);
                    switch (helper.getLayoutPosition() % 3) {
                        case 0:
                            helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                            break;
                        case 1:
                            helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                            break;
                        case 2:
                            helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                            break;
                    }
                    helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");
                    String msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
                    break;
            }
        }
    }

}
