package com.binary.hdwallpaper.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by duong on 9/6/2017.
 */

public class ImageList {
    @SerializedName("HD_WALLPAPER")
    @Expose
    private ArrayList<Image> arrImage;

    public ArrayList<Image> getArrImage() {
        return arrImage;
    }

    public void setArrImage(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }

    public ImageList(ArrayList<Image> arrImage) {

        this.arrImage = arrImage;
    }
}
