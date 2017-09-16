package com.binary.hdwallpaper.di.component;

import android.support.v4.app.Fragment;

import com.binary.hdwallpaper.di.module.AppModule;
import com.binary.hdwallpaper.ui.main.MainActivity;
import com.binary.hdwallpaper.di.ActivityScope;
import com.binary.hdwallpaper.di.module.NetModule;
import com.binary.hdwallpaper.ui.main.category.CategoryFragment;

import dagger.Component;

/**
 * Created by duong on 9/12/2017.
 */
@ActivityScope
@Component(dependencies = NetComponent.class, modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(CategoryFragment categoryFragment);
}
