package com.binary.hdwallpaper.ui.main.category;

import android.app.ProgressDialog;

import com.binary.hdwallpaper.base.Presenter;

/**
 * Created by duong on 9/16/2017.
 */

public interface CategoryMvpPresenter<V extends CategoryView> extends Presenter<V> {
    void getCategory(ProgressDialog progressDialog);
}
