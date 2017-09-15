package com.binary.hdwallpaper.di.module;

import com.binary.hdwallpaper.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duong on 9/8/2017.
 */
@Module
public class NetModule {
    public static Retrofit retrofit = null;

    @Provides
    @Singleton
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
