package cn.com.nggirl.ngdemo.launchmode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.com.nggirl.ngdemo.R;

public class LaunchModeDefaultC extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, LaunchModeDefaultC.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode_detail);
    }
}
