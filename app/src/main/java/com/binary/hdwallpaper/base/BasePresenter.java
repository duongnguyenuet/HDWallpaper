package com.binary.hdwallpaper.base;

/**
 * Created by duong on 9/12/2017.
 */

public class BasePresenter<V extends View> implements Presenter<V> {
    private V mMvpView;

    @Override
    public void attachView(V view) {
        mMvpView = view;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached(){
        return mMvpView != null;
    }

    public V getmMvpView() {
        return mMvpView;
    }

    public void checkViewAttached(){
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(View) before" +
                    " requesting data to the Presenter");
        }
    }
}
