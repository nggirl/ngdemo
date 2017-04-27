package cn.com.nggirl.ngdemo.retrofit;


import java.util.List;

import cn.com.nggirl.ngdemo.business.bean.Contributor;
import cn.com.nggirl.ngdemo.core.BasePresenter;
import cn.com.nggirl.ngdemo.core.BaseView;

public interface RetrofitDemoContract {

    interface View extends BaseView<Presenter> {
        void showContributors(List<Contributor> contributors);

        void showContributorsError(String message);

        void showContributorsEmpty();

        void showDownloadProgress(String progress);
    }

    interface Presenter extends BasePresenter {
        void contributors(String owner, String repo);

        void downloadFile(String url);
    }
}
