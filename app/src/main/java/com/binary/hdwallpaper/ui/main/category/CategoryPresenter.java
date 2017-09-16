package com.binary.hdwallpaper.ui.main.category;

import android.app.ProgressDialog;

import com.binary.hdwallpaper.api.WallpaperAPI;
import com.binary.hdwallpaper.base.BasePresenter;
import com.binary.hdwallpaper.models.CategoryList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by duong on 9/16/2017.
 */

public class CategoryPresenter<V extends CategoryView> extends BasePresenter<V> implements CategoryMvpPresenter<V> {
    private Retrofit retrofit;

    @Inject
    public CategoryPresenter(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void getCategory(final ProgressDialog progressDialog) {
        getmMvpView().showProgress(progressDialog);
        Call<CategoryList> call = retrofit.create(WallpaperAPI.class).getCategory();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                CategoryList categoryList = response.body();
                getmMvpView().hideProgress(progressDialog);
                getmMvpView().showCategory(categoryList.getArrCategory());
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                getmMvpView().hideProgress(progressDialog);
            }
        });
    }
}
