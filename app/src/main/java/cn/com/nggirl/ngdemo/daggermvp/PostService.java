package cn.com.nggirl.ngdemo.daggermvp;

import java.util.List;

import cn.com.nggirl.ngdemo.daggermvp.data.Post;
import retrofit2.http.GET;
import rx.Observable;

public interface PostService {

    @GET("/posts")
    Observable<List<Post>> getPostList();
}
