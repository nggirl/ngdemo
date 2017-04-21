package cn.com.nggirl.ngdemo.anim;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.model.DataServer;
import cn.com.nggirl.ngdemo.pullzoom.PullZoomRecyclerViewActivity;

/**
 * Android属性动画的用法
 */
public class ViewPropertyAnimationActivity extends AppCompatActivity {
    public static final String TAG = ViewPropertyAnimationActivity.class.getSimpleName();

    private LinearLayout mSearchbar;
    private Button btnPlay;
    private TextView tvContent;
    private RecyclerView mRecyclerView;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewPropertyAnimationActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mSearchbar = (LinearLayout) findViewById(R.id.ll_search_bar);
        btnPlay = (Button) findViewById(R.id.btn_view_anim_play);
        tvContent = (TextView) findViewById(R.id.tv_view_anim);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rl_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AnimationAdapter());
        initAnimations();
    }

    private void initAnimations() {
        //mSearchbar.animate().setInterpolator(new BounceInterpolator()).alpha(0).alpha(1).setDuration(5000);
    }

    private void startAnim() {
        ObjectAnimator anim = ObjectAnimator.ofObject(tvContent,
                "color",
                new ColorEvaluator(),
                "#0000FF",
                "#FF0000");
        anim.setDuration(5000);
        anim.start();
    }

    public class AnimationAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
        public AnimationAdapter() {
            super(R.layout.layout_animation, DataServer.getSampleData(100));
        }

        @Override
        protected void convert(BaseViewHolder helper, Status item) {
            helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetText).addOnClickListener(R.id.tweetName);
            switch (helper.getLayoutPosition() %
                    3) {
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

        }
    }
}
