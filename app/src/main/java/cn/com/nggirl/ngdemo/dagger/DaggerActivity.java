package cn.com.nggirl.ngdemo.dagger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import cn.com.nggirl.ngdemo.R;
import dagger.android.AndroidInjection;

public class DaggerActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DaggerActivity.class));
    }

    @Inject
    NetworkApi mNetworkApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        boolean injected = mNetworkApi == null ? false : true;
        TextView userAvailable = (TextView) findViewById(R.id.target);
        userAvailable.setText("Dependency injection worked: " + String.valueOf(injected));
    }
}
