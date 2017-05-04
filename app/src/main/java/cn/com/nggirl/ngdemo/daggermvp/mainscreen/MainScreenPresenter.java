package cn.com.nggirl.ngdemo.daggermvp.mainscreen;

import java.util.List;

import javax.inject.Inject;

import cn.com.nggirl.ngdemo.daggermvp.data.Post;
import cn.com.nggirl.ngdemo.daggermvp.PostService;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainScreenPresenter implements MainScreenContract.Presenter {

    public Retrofit retrofit;
    MainScreenContract.View mView;

    @Inject
    public MainScreenPresenter(Retrofit retrofit, MainScreenContract.View view) {
        this.retrofit = retrofit;
        mView = view;
    }

    @Override
    public void loadPost() {
        retrofit.create(PostService.class).getPostList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        mView.showPosts(posts);
                    }
                });
    }
}
