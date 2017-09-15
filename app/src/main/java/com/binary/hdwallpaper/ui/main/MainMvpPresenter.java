package com.binary.hdwallpaper.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.binary.hdwallpaper.base.Presenter;
import com.binary.hdwallpaper.models.Image;

import java.util.List;

/**
 * Created by duong on 9/12/2017.
 */

public interface MainMvpPresenter<V extends MainView> extends Presenter<V> {

    void getLatest(ProgressDialog progressDialog);

    void getCategoryItem(ProgressDialog progressDialog,String catId);

    void creatImageViewer(Context context, View view, int position, List<Image> images);

    void createPopUpMenu(Context context, View view, int position, List<Image> images);
}

