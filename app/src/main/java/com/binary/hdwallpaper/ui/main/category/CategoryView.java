package com.binary.hdwallpaper.ui.main.category;


import com.binary.hdwallpaper.base.View;
import com.binary.hdwallpaper.models.Category;

import java.util.ArrayList;

/**
 * Created by duong on 9/16/2017.
 */

public interface CategoryView extends View {
    void showCategory(ArrayList<Category> categories);
}
