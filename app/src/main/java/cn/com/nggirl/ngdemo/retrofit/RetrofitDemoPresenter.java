package cn.com.nggirl.ngdemo.retrofit;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.com.nggirl.ngdemo.FileOperationUtils;
import cn.com.nggirl.ngdemo.business.bean.Contributor;
import cn.com.nggirl.ngdemo.business.model.ServerModel;
import cn.com.nggirl.ngdemo.core.CoreUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

class RetrofitDemoPresenter implements RetrofitDemoContract.Presenter {
    private static final String TAG = RetrofitDemoPresenter.class.getSimpleName();

    private RetrofitDemoContract.View mTasksView;
    private CompositeSubscription mSubscriptions;

    RetrofitDemoPresenter(RetrofitDemoContract.View view) {
        mSubscriptions = new CompositeSubscription();
        if (view == null) {
            throw new NullPointerException("tasksView cannot be null!");
        }
        this.mTasksView = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
        mTasksView = null;
    }

    @Override
    public void contributors(String owner, String repo) {
        final Observable<List<Contributor>> call = ServerModel.getInstance().contributors(owner, repo);
        Subscriber<List<Contributor>> subscriber = new Subscriber<List<Contributor>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mTasksView.showContributorsError(e.getMessage());
            }

            @Override
            public void onNext(List<Contributor> list) {
                if (list == null || list.isEmpty()) {
                    mTasksView.showContributorsEmpty();
                } else {
                    mTasksView.showContributors(list);
                }
            }
        };

        final Subscription subscription = CoreUtil.subscribe(call, subscriber);
        mSubscriptions.add(subscription);
    }

    @Override
    public void downloadFile(final String url) {
        final Observable<ResponseBody> observable = RemoteRepository.getInstance().downloadFile(url);

        Subscriber<ResponseBody> subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "download failed " + e.getMessage());

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                final File file = writeResponseBodyToDisk(responseBody, getFileNameFromUrl(url));

                if (file == null) {
                    Log.d(TAG, "download failed");
                    return;
                }

                Log.d(TAG, "download success " + file.getAbsolutePath() + " file size " + file.length());
            }
        };

        final Subscription subscription = CoreUtil.subscribe(observable, subscriber);
        mSubscriptions.add(subscription);
    }

    /**
     * 将下载的内容根据文件名称保存在项目的文件路径下
     * <p>
     * 路径为：/storage/emulated/0/nggirl/cache/
     *
     * @param body
     * @param fileName
     * @return
     */
    private File writeResponseBodyToDisk(ResponseBody body, String fileName) {
        String filePath = FileOperationUtils.getExternalCacheDirectory();

        try {
            File futureStudioIconFile = new File(filePath + "//" + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();
                return futureStudioIconFile;
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 根据文件路径获取文件名称
     * <p>
     * 根据文件的路径来获取文件名称，包含有后缀
     * 图片文件会根据 @ 符号进行切分，以获取真实名称
     * 其他文件中不包含 @ 符号，会返回文件名
     *
     * @param url
     * @return
     */
    private String getFileNameFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        } else {
            Uri parse = Uri.parse(url);
            String path = parse.getPath();// 获取文件路径包含有文件名称
            if (!TextUtils.isEmpty(path)) {
                String substring = path.substring(path.lastIndexOf("/") + 1, path.length());// 获取的名称包含后面的图片限制信息
                if (substring.contains("@")) {
                    return substring.substring(0, substring.indexOf("@"));// 去除限制信息，获得名字，包含后缀
                } else {
                    return substring;
                }
            }
        }

        return null;
    }
}
