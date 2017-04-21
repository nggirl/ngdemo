package cn.com.nggirl.ngdemo.launchmode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.com.nggirl.ngdemo.R;

public class LaunchModeDefaultA extends AppCompatActivity {

    private boolean isSingleInstance;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LaunchModeDefaultA.class));
    }

    public static void start(Context context, boolean isSingleInstance) {
        final Intent intent = new Intent(context, LaunchModeDefaultA.class);
        intent.putExtra("isSingleInstance", isSingleInstance);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode_detail);

        isSingleInstance = getIntent().getBooleanExtra("isSingleInstance", false);
        findViewById(R.id.btn_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSingleInstance) {
                    LaunchModeSingleInstanceB.start(LaunchModeDefaultA.this);
                } else
                    LaunchModeSingleTaskB.start(LaunchModeDefaultA.this);
            }
        });
    }

}
