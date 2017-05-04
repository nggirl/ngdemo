package cn.com.nggirl.ngdemo.daggermvp.mainscreen;

import cn.com.nggirl.ngdemo.daggermvp.data.component.NetComponent;
import cn.com.nggirl.ngdemo.daggermvp.util.CustomScope;
import dagger.Component;

@CustomScope
@Component(dependencies = NetComponent.class, modules = MainScreenModule.class)
public interface MainScreenComponent {
    void inject(MainActivity activity);
}
