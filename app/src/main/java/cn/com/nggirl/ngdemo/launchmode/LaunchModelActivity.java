package cn.com.nggirl.ngdemo.launchmode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.com.nggirl.ngdemo.R;

/**
 * Explain Activity Launch Mode With Examples
 * http://www.songzhw.com/2016/08/09/explain-activity-launch-mode-with-examples/
 */
public class LaunchModelActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        findViewById(R.id.btn_default_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchModelDefaultA.start(LaunchModelActivity.this);
            }
        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, LaunchModelActivity.class));
    }

}
