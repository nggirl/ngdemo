package cn.com.nggirl.ngdemo.business;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper mHelper;
    private static Retrofit mRetrofit;

    private RetrofitHelper() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com");
        OkHttpClient client;
        client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(10_000, TimeUnit.MILLISECONDS)
                .readTimeout(10_000, TimeUnit.MILLISECONDS)
                .build();

        mRetrofit = builder.client(client).build();
    }

    public static void init() {
        getInstance();
    }

    public static RetrofitHelper getInstance() {
        if (mHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (mHelper == null) {
                    mHelper = new RetrofitHelper();
                }
            }
        }
        return mHelper;
    }

    public static <T> T createApi(Class<T> clz) {
        return mRetrofit.create(clz);
    }
}
