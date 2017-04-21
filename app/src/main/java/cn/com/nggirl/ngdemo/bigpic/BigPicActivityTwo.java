package cn.com.nggirl.ngdemo.bigpic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.piasy.biv.view.BigImageView;

import cn.com.nggirl.ngdemo.MainActivity;
import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.launchmode.LaunchModeActivity;

public class BigPicActivityTwo extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, BigPicActivityTwo.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic_two);

        BigImageView bigImageView = (BigImageView) findViewById(R.id.mBigImage);

        bigImageView.showImage(Uri.parse("https://photosd.nggirl.com.cn/work/7bca1abde2fc451cb6535e1a3ddfdfbb.jpg@70Q"));
    }


}
