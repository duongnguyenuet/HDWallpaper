package com.binary.hdwallpaper.base;

/**
 * Created by duong on 9/12/2017.
 */

public interface Presenter<V extends View> {
    void attachView(V view);
    void detachView();
}
