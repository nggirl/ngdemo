package cn.com.nggirl.ngdemo.bannerpicscale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.nggirl.ngdemo.R;

/**
 * 焦点图图片展示效果
 */

public class BannerPicScaleActivity extends AppCompatActivity {

    @BindView(R.id.buttonWidepic)
    Button buttonWidepic;
    @BindView(R.id.buttonRecpic)
    Button buttonRecpic;
    @BindView(R.id.buttonPortritPic)
    Button buttonPortritPic;

    public static void start(Context context) {
        context.startActivity(new Intent(context, BannerPicScaleActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pic_scale);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.buttonWidepic, R.id.buttonRecpic, R.id.buttonPortritPic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonWidepic:
                BannerPicScaleDetailsActivity.start(BannerPicScaleActivity.this,
                        ScaleTypeConstants.TYPE_WIDE_PIC);
                break;
            case R.id.buttonRecpic:
                BannerPicScaleDetailsActivity.start(BannerPicScaleActivity.this,
                        ScaleTypeConstants.TYPE_REC_PIC);
                break;
            case R.id.buttonPortritPic:
                BannerPicScaleDetailsActivity.start(BannerPicScaleActivity.this,
                        ScaleTypeConstants.TYPE_PORTRIT_PIC);
                break;
        }
    }
}