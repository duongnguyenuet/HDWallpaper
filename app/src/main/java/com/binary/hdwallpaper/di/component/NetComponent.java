package com.binary.hdwallpaper.di.component;

import com.binary.hdwallpaper.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by duong on 9/12/2017.
 */
@Singleton
@Component (modules = NetModule.class)
public interface NetComponent {
    Retrofit retrofit();
}
