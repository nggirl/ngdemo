package cn.com.nggirl.ngdemo.bannerpicscale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.nggirl.ngdemo.R;

public class BannerPicScaleDetailsActivity extends AppCompatActivity {

    @BindView(R.id.product_details_view_pager)
    BGABanner banner;
    private int scaleType;

    public static void start(Context context, int type) {
        final Intent intent = new Intent(context, BannerPicScaleDetailsActivity.class);
        intent.putExtra("scaleType", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pic_scale_detail);
        ButterKnife.bind(this);
        scaleType = getIntent().getIntExtra("scaleType", 0);
        int height = 0;
        int width = 0;
        int h;
        DisplayMetrics d = getResources().getDisplayMetrics();
        List<String> list = new ArrayList<>();
        switch (scaleType) {
            case ScaleTypeConstants.TYPE_PORTRIT_PIC:
                height = 1220;
                width = 457;
                list.add("R.drawable.protrit");
                list.add("R.drawable.protrit");
                list.add("R.drawable.protrit");
                break;
            case ScaleTypeConstants.TYPE_WIDE_PIC:
                height = 1220;
                width = 1880;
                list.add("R.drawable.wide");
                list.add("R.drawable.wide");
                list.add("R.drawable.wide");
                break;
            case ScaleTypeConstants.TYPE_REC_PIC:
                height = 800;
                width = 800;
                list.add("R.drawable.rec");
                list.add("R.drawable.rec");
                list.add("R.drawable.rec");
                break;
        }

        h = d.widthPixels * height / width;

        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.height = h;
        banner.setLayoutParams(params);

        banner.setAdapter(new BGABanner.Adapter<SimpleDraweeView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, SimpleDraweeView itemView, String model, int position) {
                itemView.setImageURI("res://" + getPackageName() + "/" + R.drawable.wide);
            }
        });
        banner.setData(R.layout.item_banner_pic, list, null);
    }
}
