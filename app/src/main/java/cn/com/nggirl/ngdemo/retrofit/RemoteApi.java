package cn.com.nggirl.ngdemo.retrofit;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface RemoteApi {
    @Streaming
    @GET
    Observable<ResponseBody> downloadFileFromNet(@Url String fileUrl);
}
