package cn.com.nggirl.ngdemo.business.model;


import java.util.List;

import cn.com.nggirl.ngdemo.business.RetrofitHelper;
import cn.com.nggirl.ngdemo.business.bean.Contributor;
import cn.com.nggirl.ngdemo.business.protocol.ServerApi;
import rx.Observable;

public class ServerModel {

    private ServerApi mShareApi;
    private static ServerModel mShareModel;

    public static ServerModel getInstance() {
        if (mShareModel == null) {
            synchronized (ServerModel.class) {
                if (mShareModel == null) {
                    mShareModel = new ServerModel();
                }
            }
        }
        return mShareModel;
    }

    private ServerModel() {
        mShareApi = RetrofitHelper.createApi(ServerApi.class);
    }

    public Observable<List<Contributor>> contributors(String owner, String repo) {
        return mShareApi.contributors(owner, repo);
    }
}
