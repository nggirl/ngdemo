package cn.com.nggirl.ngdemo.titlebargradient;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.nggirl.ngdemo.ColorUtil;
import cn.com.nggirl.ngdemo.CommonAdapter;
import cn.com.nggirl.ngdemo.R;

/**
 * 标题栏随着RecyclerView滚动背景渐变
 */
public class TitlebarGradientActivity extends AppCompatActivity {
    public static final String TAG = TitlebarGradientActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RelativeLayout rlTopbar;
    private TextView tvTitle;

    private int totalDy;
    private float maxScrollDistance = 1080;

    public static Intent newInstance(Context context) {
        return new Intent(context, TitlebarGradientActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlebar_gradient);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        rlTopbar = (RelativeLayout) findViewById(R.id.rl_topbar_box);
        tvTitle = (TextView) findViewById(R.id.title);

        CommonAdapter adapter = new CommonAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                scrollChangeTitlebar(totalDy);
                if (!recyclerView.canScrollVertically(-1)) {
                    //置顶了
                    Log.d(TAG, "dy " + dy + " totalDy " + totalDy);
                    // http://blog.csdn.net/u010940300/article/details/49252395
                }
            }
        });
    }


    private void scrollChangeTitlebar(int dy) {
        if (maxScrollDistance <= 0) {
            Log.d(TAG, "return maxScrollDistance " + maxScrollDistance);
            return;
        }

        float fraction = Math.abs(dy) * 1.0f / (maxScrollDistance);
        Log.d(TAG, "fraction " + fraction);

        if (fraction > 1.0) {
            fraction = 1;
        }

        int mSearchBarCurrentColor;

        if (fraction < 0.05) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mSearchBarCurrentColor = getResources().getColor(R.color.transparent, getTheme());
            } else {
                mSearchBarCurrentColor = getResources().getColor(R.color.transparent);
            }
            rlTopbar.setBackgroundColor(mSearchBarCurrentColor);
            tvTitle.setVisibility(View.INVISIBLE);
        } else {
            mSearchBarCurrentColor = ColorUtil.getNewColorByStartEndColor(this,
                    fraction,
                    R.color.transparent, R.color.red);
            rlTopbar.setBackgroundColor(mSearchBarCurrentColor);

            rlTopbar.setBackgroundColor(mSearchBarCurrentColor);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setAlpha(fraction);
        }

    }
}
