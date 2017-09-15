package com.binary.hdwallpaper;

import android.app.Application;

import com.binary.hdwallpaper.di.component.AppComponent;
import com.binary.hdwallpaper.di.component.DaggerAppComponent;
import com.binary.hdwallpaper.di.component.DaggerNetComponent;
import com.binary.hdwallpaper.di.component.NetComponent;
import com.binary.hdwallpaper.di.module.AppModule;
import com.binary.hdwallpaper.di.module.NetModule;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

/**
 * Created by duong on 9/12/2017.
 */

public class MyApplication extends Application {
    private NetComponent mNetComponent;
    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        initFresco();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public void initComponent(){
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .netComponent(mNetComponent)
                .build();
    }

    private void initFresco(){
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,config);
    }

    public AppComponent getmAppComponent(){
        return mAppComponent;
    }
}
