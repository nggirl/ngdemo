package cn.com.nggirl.ngdemo.daggermvp.data.component;

import javax.inject.Singleton;

import cn.com.nggirl.ngdemo.daggermvp.data.module.AppModule;
import cn.com.nggirl.ngdemo.daggermvp.data.module.NetModule;
import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
}
