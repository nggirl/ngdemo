package cn.com.nggirl.ngdemo.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;

import cn.com.nggirl.ngdemo.business.RetrofitHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitHelper.init();
        // MUST use app context to avoid memory leak!
        // load with fresco
        BigImageViewer.initialize(FrescoImageLoader.with(this));

        // or load with glide
        // BigImageViewer.initialize(GlideImageLoader.with(appContext));

        Stetho.initializeWithDefaults(this);
    }
}
