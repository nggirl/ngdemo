package cn.com.nggirl.ngdemo.scaletransitionanim;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.nggirl.ngdemo.MainActivity;
import cn.com.nggirl.ngdemo.R;

public class ScaleZoomMainActivity extends AppCompatActivity {

    private FrameLayout mContentContainer;
    private RecyclerView mRecyclerView;
    private PreviewLayout mPreviewLayout;

    private List<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
    private LinearLayout.LayoutParams mLayoutParams;
    private int mStatusBarHeight;
    private int[] mPadding = new int[4];
    private int mSolidWidth = 0;
    private int mSolidHeight = 0;
    private Rect mRVBounds = new Rect();
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_main);

        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        mStatusBarHeight = getResources().getDimensionPixelSize(resId);

        mContentContainer = (FrameLayout) findViewById(android.R.id.content);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        mLayoutParams = new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.widthPixels / 3);

        List<String> urls = ImageUrlConfig.getUrls();

        for (int i = 0; i < urls.size(); i++) {
            mThumbViewInfoList.add(new ThumbViewInfo(urls.get(i), i));
        }

        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new QuickAdapter(urls));

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPreviewLayout = new PreviewLayout(ScaleZoomMainActivity.this);
                mPreviewLayout.setData(mThumbViewInfoList, position);
                mPreviewLayout.startScaleUpAnimation();
                mContentContainer.addView(mPreviewLayout);
            }

        });

        mRecyclerView.addOnScrollListener(new MyRecyclerOnScrollListener());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mRecyclerView.getGlobalVisibleRect(mRVBounds);

            assembleDataList();
        }
    }

    public static Intent newInstance(MainActivity mainActivity) {
        return new Intent(mainActivity, ScaleZoomMainActivity.class);
    }

    private class MyRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                assembleDataList();
            }
        }
    }

    private void assembleDataList() {
        computeBoundsForward(mGridLayoutManager.findFirstCompletelyVisibleItemPosition());

        computeBoundsBackward(mGridLayoutManager.findFirstCompletelyVisibleItemPosition());
    }

    /**
     * 从第一个完整可见item顺序遍历
     */
    private void computeBoundsForward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();

            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.thumb_iv);

                thumbView.getGlobalVisibleRect(bounds);

                if (mSolidWidth * mSolidHeight == 0) {
                    mPadding[0] = thumbView.getPaddingLeft();
                    mPadding[1] = thumbView.getPaddingTop();
                    mPadding[2] = thumbView.getPaddingRight();
                    mPadding[3] = thumbView.getPaddingBottom();
                    mSolidWidth = bounds.width();
                    mSolidHeight = bounds.height();
                }

                bounds.left = bounds.left + mPadding[0];
                bounds.top = bounds.top + mPadding[1];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.bottom = bounds.top + mSolidHeight - mPadding[3];
            } else {
                bounds.left = i % 3 * mSolidWidth + mPadding[0];
                bounds.top = mRVBounds.bottom + mPadding[1];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.bottom = bounds.top + mSolidHeight - mPadding[3];
            }
            bounds.offset(0, -mStatusBarHeight);

            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos - 1; i >= 0; i--) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();

            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.thumb_iv);

                thumbView.getGlobalVisibleRect(bounds);

                bounds.left = bounds.left + mPadding[0];
                bounds.bottom = bounds.bottom - mPadding[3];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.top = bounds.bottom - mSolidHeight + mPadding[1];
            } else {
                bounds.left = i % 3 * mSolidWidth + mPadding[0];
                bounds.bottom = mRVBounds.top - mPadding[3];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.top = bounds.bottom - mSolidHeight + mPadding[1];
            }
            bounds.offset(0, -mStatusBarHeight);

            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    @Override
    public void onBackPressed() {
        if (mContentContainer.getChildAt(mContentContainer.getChildCount() - 1) instanceof PreviewLayout) {
            mPreviewLayout.startScaleDownAnimation();
            return;
        }

        super.onBackPressed();
    }

    private class QuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        QuickAdapter(List<String> list) {
            super(R.layout.item_pic_thumb, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, String url) {
            helper.itemView.setLayoutParams(mLayoutParams);
            final ImageView thumbView = helper.getView(R.id.thumb_iv);
            Glide.with(ScaleZoomMainActivity.this)
                    .load(url)
                    .into(thumbView);
        }

    }
}