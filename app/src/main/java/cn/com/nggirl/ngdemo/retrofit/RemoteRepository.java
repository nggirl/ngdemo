package cn.com.nggirl.ngdemo.retrofit;

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;

import cn.com.nggirl.ngdemo.FileOperationUtils;
import cn.com.nggirl.ngdemo.business.RetrofitHelper;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RemoteRepository {
    // 默认文件保存路径
    private String filePath = FileOperationUtils.getExternalCacheDirectory();

    private RemoteApi api;
    private volatile static RemoteRepository model;

    public static RemoteRepository getInstance() {
        if (model == null) {
            synchronized (RemoteRepository.class) {
                if (model == null) {
                    model = new RemoteRepository();
                }
            }
        }
        return model;
    }

    private RemoteRepository() {
        api = RetrofitHelper.createApi(RemoteApi.class);
    }

    /**
     * 根据链接下载文件
     *
     * @param fileUrl
     */
    public Observable<ResponseBody> downloadFile(@Nullable final String fileUrl) {
        return api.downloadFileFromNet(fileUrl);
    }


    /**
     * 根据链接下载文件
     *
     * @param fileUrl
     */
    /*public void downloadFile(@Nullable final String fileUrl) {
        api.downloadFileFromNet(fileUrl)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        return writeResponseBodyToDisk(responseBody, getFileNameFromUrl(fileUrl));
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(File file) {
                        // 获取 file
                    }
                });
    }*/


    /**
     * 根据链接下载文件
     * <p>
     * 下载完成后可以获得对应的文件对象
     * 并且将下载的结果（成功，失败）返回
     *
     * @param fileUrl
     * @param subscriber
     */
    public void downloadFile(final String fileUrl, Subscriber<File> subscriber) {
        api.downloadFileFromNet(fileUrl)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, File>() {

                    @Override
                    public File call(ResponseBody responseBody) {
                        return writeResponseBodyToDisk(responseBody, getFileNameFromUrl(fileUrl));
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 下载文件并以文件名进行保存
     *
     * @param url
     */
    public void download(@Nullable final String url) {
        api.downloadFileFromNet(url)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        writeResponseBodyToDisk(responseBody, getFileNameFromUrl(url));
                    }
                });
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

    /**
     * 自定义文件路径，默认为项目的cache文件下
     * 需要在下载前进行设置
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
