package com.binary.hdwallpaper.base;

import android.app.ProgressDialog;


/**
 * Created by duong on 9/12/2017.
 */

public interface View {
    void showProgress(ProgressDialog dialog);

    void hideProgress(ProgressDialog dialog);
}
