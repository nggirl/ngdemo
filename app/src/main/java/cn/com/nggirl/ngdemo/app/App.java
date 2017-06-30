package cn.com.nggirl.ngdemo.app;

import android.app.Application;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.stetho.Stetho;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;

import java.util.HashSet;
import java.util.Set;

import cn.com.nggirl.ngdemo.business.RetrofitHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitHelper.init();
        // MUST use app context to avoid memory leak!
        // load with fresco
        BigImageViewer.initialize(FrescoImageLoader.with(this));

        //启动日志
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                // other setters
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(this, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);

        // or load with glide
        // BigImageViewer.initialize(GlideImageLoader.with(appContext));

        Stetho.initializeWithDefaults(this);
    }
}
