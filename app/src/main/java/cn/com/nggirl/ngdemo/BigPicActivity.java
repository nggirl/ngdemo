package cn.com.nggirl.ngdemo;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

public class BigPicActivity extends AppCompatActivity {
    private SubsamplingScaleImageView scaleImageView;
    private SubsamplingScaleImageView scaleImageView2;
    public static Intent newInstance(MainActivity mainActivity) {
        return new Intent(mainActivity, BigPicActivity.class);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        scaleImageView = (SubsamplingScaleImageView) findViewById(R.id.view_big_pic);
        scaleImageView2 = (SubsamplingScaleImageView) findViewById(R.id.view_big_pic2);
        loadBigImageFile("https://photosd.nggirl.com.cn/work/1b0ae16d56cc42aa8a4f5a3cd02c576c.jpg@610w_610h_1e.jpg", scaleImageView);
        loadBigImageFile("https://photosd.nggirl.com.cn/work/1b0ae16d56cc42aa8a4f5a3cd02c576c.jpg@610w_610h_1e.jpg", scaleImageView2);
    }

    public static final String TAG = BigPicActivity.class.getSimpleName();

    private void loadBigImageFile(String url, final SubsamplingScaleImageView bigPic) {
        OkGo.get(url)//
                .tag(this)//
                .execute(new FileCallback() {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        Log.d(TAG, "onSuccess " + file.getAbsolutePath());
                        // file 即为文件数据，文件保存在指定目录
                        if (isFinishing())
                            return;

                        bigPic.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        Log.d(TAG, "downloadProgress " + progress + " total " + totalSize);
                    }
                });
    }


}
