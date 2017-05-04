package cn.com.nggirl.ngdemo.daggermvp.mainscreen;

import cn.com.nggirl.ngdemo.daggermvp.util.CustomScope;
import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {
    private final MainScreenContract.View mView;


    public MainScreenModule(MainScreenContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainScreenContract.View providesMainScreenContractView() {
        return mView;
    }

}
