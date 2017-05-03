package cn.com.nggirl.ngdemo.dagger;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {IDaggerActivitySubcomponent.class})
public abstract class DaggerModule {

    @Binds
    @IntoMap
    @ActivityKey(DaggerActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindYourActivityInjectorFactory(IDaggerActivitySubcomponent.Builder builder);
}
