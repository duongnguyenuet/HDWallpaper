package com.binary.hdwallpaper.api;

import com.binary.hdwallpaper.models.CategoryList;
import com.binary.hdwallpaper.models.ImageList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by duong on 9/6/2017.
 */

public interface WallpaperAPI {
    @GET("api.php?latest")
    Call<ImageList> getLastest();

    @GET("api.php?cat_list")
    Call<CategoryList> getCategory();

    @GET("api.php")
    Call<ImageList> getCategoryItem(@Query("cat_id") String catId);
}
