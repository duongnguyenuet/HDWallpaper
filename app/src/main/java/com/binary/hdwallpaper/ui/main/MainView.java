package com.binary.hdwallpaper.ui.main;

import android.widget.PopupMenu;

import com.binary.hdwallpaper.base.View;
import com.binary.hdwallpaper.models.Image;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duong on 9/12/2017.
 */

public interface MainView extends View {
    void showImage(ArrayList<Image> images);

    void showImageViewer(ImageViewer imageViewer);

    void showPopUpMenu(PopupMenu popupMenu);
}
