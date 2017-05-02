package cn.com.nggirl.ngdemo.databinding;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.com.nggirl.ngdemo.R;

public class DataBindingActivity extends AppCompatActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, DataBindingActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setUser(new User("Test", "User"));
        MyHandlers handlers = new MyHandlers();
        Presenter presenter = new Presenter();
        binding.setHandlers(handlers);
        binding.setPresenter(presenter);
    }

    public class MyHandlers {
        public void onClickFriend(View view) {
            Toast.makeText(DataBindingActivity.this, "onClick ", Toast.LENGTH_SHORT).show();
        }
    }

    public class Presenter {
        public void onSaveClick(Task task) {
            Toast.makeText(DataBindingActivity.this, "onSaveClick ", Toast.LENGTH_SHORT).show();
        }
    }

    public class Task {

    }
}
