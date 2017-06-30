package cn.com.nggirl.ngdemo.fresco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.nggirl.ngdemo.R;

public class FrescoViewBigPicActivity extends AppCompatActivity {

    @BindView(R.id.iv_fresco_big_pic)
    SimpleDraweeView mIvFrescoBigPic;

    public static void start(Context context) {
        context.startActivity(new Intent(context, FrescoViewBigPicActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_view_big_pic);
        ButterKnife.bind(this);


        mIvFrescoBigPic.setAspectRatio(0.1812063163f);
        mIvFrescoBigPic.setImageURI("file://" + "/storage/emulated/0/DCIM/big_pic/超长图3.jpg");

    }


}
