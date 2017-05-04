package cn.com.nggirl.ngdemo.daggermvp.mainscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.daggermvp.App;
import cn.com.nggirl.ngdemo.daggermvp.data.Post;
import cn.com.nggirl.ngdemo.daggermvp.util.EspressoIdlingResource;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Inject
    MainScreenPresenter mainPresenter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        listView = (ListView) findViewById(R.id.my_list);
        list = new ArrayList<>();

        DaggerMainScreenComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .mainScreenModule(new MainScreenModule(this))
                .build().inject(this);

        //Increment the counter before making a network request
        EspressoIdlingResource.increment();

        //Call the method in MainPresenter to make Network Request
        mainPresenter.loadPost();
    }

    @Override
    public void showPosts(List<Post> posts) {
        //Loop through the posts and get the title of the post and add it to our list object
        for (int i = 0; i < posts.size(); i++) {
            list.add(posts.get(i).getTitle());
        }
        //Create the array adapter and set it to list view
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        //Show error message Toast
        Toast.makeText(getApplicationContext(), "Error" + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showComplete() {
        //Show completed message Toast
        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_SHORT).show();

    }
}
