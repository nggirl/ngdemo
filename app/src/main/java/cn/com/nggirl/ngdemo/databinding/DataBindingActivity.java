package cn.com.nggirl.ngdemo.databinding;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.com.nggirl.ngdemo.R;

public class DataBindingActivity extends AppCompatActivity implements DataBindingContract.View {
    public static void start(Context context) {
        context.startActivity(new Intent(context, DataBindingActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        DataBindingPresenter presenter = new DataBindingPresenter(this);
        TemperatureData temperatureData = new TemperatureData("Hamburg", "10");
        binding.setTemp(temperatureData);
        binding.setPresenter(presenter);
    }

    @Override
    public void showData(TemperatureData temperatureData) {
        String celsius = temperatureData.getCelsius();
        Toast.makeText(this, celsius, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startSecondActivity() {
        DataBindingRecyclerViewActivity.start(DataBindingActivity.this);
    }
}
