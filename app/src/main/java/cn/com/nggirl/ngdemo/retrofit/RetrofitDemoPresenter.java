package cn.com.nggirl.ngdemo.retrofit;

import java.util.List;

import cn.com.nggirl.ngdemo.business.bean.Contributor;
import cn.com.nggirl.ngdemo.business.model.ServerModel;
import cn.com.nggirl.ngdemo.core.CoreUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RetrofitDemoPresenter implements RetrofitDemoContract.Presenter {

    private RetrofitDemoContract.View mTasksView;
    private CompositeSubscription mSubscriptions;

    public RetrofitDemoPresenter(RetrofitDemoContract.View view) {
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
}
