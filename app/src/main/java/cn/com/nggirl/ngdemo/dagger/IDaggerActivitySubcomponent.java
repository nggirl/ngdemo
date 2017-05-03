package cn.com.nggirl.ngdemo.dagger;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface IDaggerActivitySubcomponent extends AndroidInjector<DaggerActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerActivity> {
    }
}
