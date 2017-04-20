package cn.com.nggirl.ngdemo;

import android.app.Application;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // MUST use app context to avoid memory leak!
        // load with fresco
        BigImageViewer.initialize(FrescoImageLoader.with(this));

        // or load with glide
//        BigImageViewer.initialize(GlideImageLoader.with(appContext));
    }
}
