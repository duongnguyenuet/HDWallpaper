package com.binary.hdwallpaper.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.binary.hdwallpaper.MyApplication;
import com.binary.hdwallpaper.R;

import com.binary.hdwallpaper.models.Image;

import com.binary.hdwallpaper.ui.main.category.CategoryFragment;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    @Inject
    MainPresenter<MainView> mMainPresenter;
    @BindView(R.id.rv_image)
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private MainAdapter adapter;
    private ArrayList<Image> arrImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initPresenter();
        init();
        initProgress();
        mMainPresenter.getLatest(progressDialog);
    }

    public void init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(arrImage);
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mMainPresenter.creatImageViewer(MainActivity.this,view,position,arrImage);
            }
        });
        adapter.setOnItemLongClickListener(new MainAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                mMainPresenter.createPopUpMenu(MainActivity.this,view,position,arrImage);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void initPresenter(){
        MyApplication application = (MyApplication) getApplication();
        application.getmAppComponent().inject(this);
        mMainPresenter.attachView(this);
    }

    public void initProgress(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Get wallpaper");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_latest) {
        } else if (id == R.id.nav_category) {
            fragmentClass = CategoryFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_layout,fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void showProgress(ProgressDialog dialog) {
        progressDialog.show();
    }

    @Override
    public void hideProgress(ProgressDialog dialog) {
        progressDialog.hide();
    }

    @Override
    public void showImage(ArrayList<Image> images) {
        arrImage.addAll(images);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showImageViewer(ImageViewer imageViewer) {
        imageViewer.show();
    }

    @Override
    public void showPopUpMenu(PopupMenu popupMenu) {
        popupMenu.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainPresenter.detachView();
    }
}
