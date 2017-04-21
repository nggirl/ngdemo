package cn.com.nggirl.ngdemo.bannerpicscale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.com.nggirl.ngdemo.R;

/**
 * 焦点图图片展示效果
 */

public class BannerPicScaleActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, BannerPicScaleActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pic_scale);

        findViewById(R.id.buttonWidepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerPicScaleDetailsActivity.start(BannerPicScaleActivity.this,
                        ScaleTypeConstants.TYPE_WIDE_PIC);
            }
        });

        findViewById(R.id.buttonRecpic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerPicScaleDetailsActivity.start(BannerPicScaleActivity.this,
                        ScaleTypeConstants.TYPE_REC_PIC);
            }
        });

        findViewById(R.id.buttonPortritPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerPicScaleDetailsActivity.start(BannerPicScaleActivity.this,
                        ScaleTypeConstants.TYPE_PORTRIT_PIC);
            }
        });
    }
}