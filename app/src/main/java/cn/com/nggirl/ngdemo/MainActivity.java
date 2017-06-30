package cn.com.nggirl.ngdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.nggirl.ngdemo.aidl.MessengerActivity;
import cn.com.nggirl.ngdemo.anim.ViewPropertyAnimationActivity;
import cn.com.nggirl.ngdemo.bannerpicscale.BannerPicScaleActivity;
import cn.com.nggirl.ngdemo.bigpic.BigPicActivity;
import cn.com.nggirl.ngdemo.bigpic.BigPicActivityTwo;
import cn.com.nggirl.ngdemo.dagger.DaggerActivity;
import cn.com.nggirl.ngdemo.databinding.DataBindingActivity;
import cn.com.nggirl.ngdemo.fresco.FrescoViewBigPicActivity;
import cn.com.nggirl.ngdemo.gallery.EasyGalleryActivity;
import cn.com.nggirl.ngdemo.keyboard.KeyboardEventActivity;
import cn.com.nggirl.ngdemo.launchmode.LaunchModeActivity;
import cn.com.nggirl.ngdemo.pullzoom.PullZoomRecyclerViewActivity;
import cn.com.nggirl.ngdemo.retrofit.RetrofitDemoActivity;
import cn.com.nggirl.ngdemo.scaletransitionanim.ScaleZoomMainActivity;
import cn.com.nggirl.ngdemo.titlebargradient.TitlebarGradientActivity;
import cn.com.nggirl.ngdemo.transition.ZoomingMainActivity;
import cn.com.nggirl.ngdemo.view.ScrollEventConflictActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_gallery)
    Button btnViewGallery;
    @BindView(R.id.btn_view_keyboard)
    Button btnViewKeyboard;
    @BindView(R.id.btn_view_anim)
    Button btnViewAnim;
    @BindView(R.id.btn_pull_zoom)
    Button btnPullZoom;
    @BindView(R.id.btn_titlebar_gradient)
    Button btnTitlebarGradient;
    @BindView(R.id.btn_zoom_view_transition)
    Button btnZoomViewTransition;
    @BindView(R.id.btn_zoom_view_transition2)
    Button btnZoomViewTransition2;
    @BindView(R.id.btn_view_big_pic)
    Button btnViewBigPic;
    @BindView(R.id.btn_view_big_pic2)
    Button btnViewBigPic2;
    @BindView(R.id.btn_view_launch_mode)
    Button btnViewLaunchMode;
    @BindView(R.id.btn_view_banner_pic_scale)
    Button btnViewBannerPicScale;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.btn_view_retrofit)
    Button btnViewRetrofit;
    @BindView(R.id.btn_view_data_binding)
    Button mBtnViewDataBinding;
    @BindView(R.id.btn_view_dagger)
    Button mBtnViewDagger;
    @BindView(R.id.btn_view_dagger_mvp)
    Button mBtnViewDaggerMvp;
    @BindView(R.id.btn_adil)
    Button mBtnAdil;
    @BindView(R.id.btn_custom_view)
    Button mBtnCustomView;
    @BindView(R.id.btn_fresco_big_pic)
    Button mBtnFrescoBigPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick({R.id.btn_gallery,R.id.btn_view_keyboard, R.id.btn_view_anim, R.id.btn_pull_zoom, R.id.btn_titlebar_gradient,
            R.id.btn_zoom_view_transition, R.id.btn_zoom_view_transition2, R.id.btn_view_big_pic,
            R.id.btn_view_big_pic2, R.id.btn_view_launch_mode, R.id.btn_view_banner_pic_scale,
            R.id.btn_view_retrofit, R.id.btn_view_data_binding, R.id.btn_view_dagger,
            R.id.btn_view_dagger_mvp, R.id.btn_adil, R.id.btn_custom_view,R.id.btn_fresco_big_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_gallery:
                List<String> list = new ArrayList<>();
                list.add("http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg");
                list.add("http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg");
                list.add("http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg");
                list.add("http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg");
                list.add("http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg");
                list.add("http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg");
                EasyGalleryActivity.start(MainActivity.this,0,list);
                break;
            case R.id.btn_fresco_big_pic:
                FrescoViewBigPicActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_keyboard:
                KeyboardEventActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_anim:
                ViewPropertyAnimationActivity.start(MainActivity.this);
                break;
            case R.id.btn_custom_view:
                ScrollEventConflictActivity.start(MainActivity.this);
                break;
            case R.id.btn_pull_zoom:
                PullZoomRecyclerViewActivity.start(MainActivity.this);
                break;
            case R.id.btn_titlebar_gradient:
                TitlebarGradientActivity.start(MainActivity.this);
                break;
            case R.id.btn_zoom_view_transition:
                ZoomingMainActivity.start(MainActivity.this);
                break;
            case R.id.btn_zoom_view_transition2:
                ScaleZoomMainActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_big_pic:
                BigPicActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_big_pic2:
                BigPicActivityTwo.start(MainActivity.this);
                break;
            case R.id.btn_view_launch_mode:
                LaunchModeActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_banner_pic_scale:
                BannerPicScaleActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_retrofit:
                RetrofitDemoActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_data_binding:
                DataBindingActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_dagger:
                DaggerActivity.start(MainActivity.this);
                break;
            case R.id.btn_view_dagger_mvp:
                cn.com.nggirl.ngdemo.daggermvp.mainscreen.MainActivity.start(MainActivity.this);
                break;
            case R.id.btn_adil:
                MessengerActivity.start(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
