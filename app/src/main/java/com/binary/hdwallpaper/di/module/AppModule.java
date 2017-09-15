package com.binary.hdwallpaper.di.module;

import com.binary.hdwallpaper.di.ActivityScope;
import com.binary.hdwallpaper.ui.main.MainMvpPresenter;
import com.binary.hdwallpaper.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by duong on 9/14/2017.
 */
@Module
public class AppModule {
    @Provides
    @ActivityScope
    MainMvpPresenter<MainView> provideMainPresenter(MainMvpPresenter<MainView> presenter){
        return presenter;
    }
}
