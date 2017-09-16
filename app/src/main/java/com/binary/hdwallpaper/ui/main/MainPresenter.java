package com.binary.hdwallpaper.ui.main;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.binary.hdwallpaper.R;
import com.binary.hdwallpaper.api.WallpaperAPI;
import com.binary.hdwallpaper.base.BasePresenter;
import com.binary.hdwallpaper.di.module.NetModule;
import com.binary.hdwallpaper.models.Image;
import com.binary.hdwallpaper.models.ImageList;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.stfalcon.frescoimageviewer.ImageViewer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by duong on 9/13/2017.
 */

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private Retrofit retrofit;

    @Inject
    public MainPresenter(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void getLatest(final ProgressDialog progressDialog) {
        getmMvpView().showProgress(progressDialog);
        Call<ImageList> call = retrofit.create(WallpaperAPI.class).getLastest();
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                getmMvpView().hideProgress(progressDialog);
                ImageList images = response.body();
                getmMvpView().showImage(images.getArrImage());
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                getmMvpView().hideProgress(progressDialog);
            }
        });
    }

    @Override
    public void getCategoryItem(final ProgressDialog progressDialog, String catId) {
        getmMvpView().showProgress(progressDialog);
        WallpaperAPI wallpaperAPI = NetModule.getClient().create(WallpaperAPI.class);
        Call<ImageList> call = wallpaperAPI.getCategoryItem(catId);
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                getmMvpView().hideProgress(progressDialog);
                ArrayList<Image> images = response.body().getArrImage();
                getmMvpView().showImage(images);
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                getmMvpView().hideProgress(progressDialog);
            }
        });
    }

    @Override
    public void createPopUpMenu(final Context context, final View view, final int position, final List<Image> images) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_open){
                    creatImageViewer(context, view, position, images);
                } else if (id == R.id.action_save){
                    Image image = images.get(position);
                    String path = Environment.getExternalStorageDirectory().getPath()
                            + "/" + Environment.DIRECTORY_DCIM + "/" +image.getCatId() + image.getId() + ".png";
                    getDownloadObservable(image.getWallpaperImage(),path)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    if (aBoolean) {
                                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else if (id == R.id.action_set_as_background){
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Setting");
                    Image image = images.get(position);
                    getImageBitmapObservable(image.getWallpaperImage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Bitmap>() {
                                @Override
                                public void accept(Bitmap bitmap) {
                                    try {
                                        WallpaperManager wallManager = WallpaperManager.getInstance(context.getApplicationContext());
                                        wallManager.setBitmap(bitmap);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                return false;
            }
        });
        getmMvpView().showPopUpMenu(popupMenu);
    }

    @Override
    public void creatImageViewer(Context context, View view, int position, List<Image> images) {
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setFailureImage(android.R.drawable.screen_background_light)
                .setProgressBarImage(android.R.drawable.screen_background_dark_transparent)
                .setPlaceholderImage(android.R.drawable.screen_background_dark);
        View v = LayoutInflater.from(context).inflate(R.layout.view_image, (ViewGroup) view.getParent(), false);
        ImageView ivBack = v.findViewById(R.id.ivBack);
        ImageViewer.Formatter<Image> formatter = new ImageViewer.Formatter<Image>() {
            @Override
            public String format(Image image) {
                return image.getWallpaperImage();
            }
        };
        final ImageViewer imageViewer = new ImageViewer.Builder(context, images)
                .setFormatter(formatter)
                .hideStatusBar(false)
                .setStartPosition(position)
                .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
                .setOverlayView(v)
                .build();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageViewer.onDismiss();
                    }
                }, 500);

            }
        });

        getmMvpView().showImageViewer(imageViewer);
    }

    private Observable<Boolean> getDownloadObservable(final String urlImg, final String path){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call(){
                try {
                    URL url = new URL(urlImg);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    File file = new File(path);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte b[] = new byte[1024];
                    int count = inputStream.read(b);
                    while (count != -1) {
                        outputStream.write(b, 0, count);
                        count = inputStream.read(b);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

                return true;
            }
        });
    }

    private Observable<Bitmap> getImageBitmapObservable(final String urlImg){
        return Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                URL url = new URL(urlImg);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(inputStream);
                return bitMap;
            }
        });
    }
}
