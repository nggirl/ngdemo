package cn.com.nggirl.ngdemo.daggermvp.mainscreen;

import java.util.List;

import cn.com.nggirl.ngdemo.daggermvp.data.Post;

public interface MainScreenContract {
    interface View {
        void showPosts(List<Post> posts);

        void showError(String message);

        void showComplete();
    }

    interface Presenter {
        void loadPost();
    }
}
