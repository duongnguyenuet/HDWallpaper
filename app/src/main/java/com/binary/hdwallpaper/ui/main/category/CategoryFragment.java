package com.binary.hdwallpaper.ui.main.category;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binary.hdwallpaper.MyApplication;
import com.binary.hdwallpaper.R;
import com.binary.hdwallpaper.models.Category;
import com.binary.hdwallpaper.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryFragment extends Fragment implements CategoryView {
    @Inject
    CategoryPresenter<CategoryView> categoryPresenter;
    @BindView(R.id.rv_category)
    RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private ArrayList<Category> arrCategory = new ArrayList<>();
    private ProgressDialog progressDialog;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this,view);
        initPresenter();
        init();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Getting Category");
        categoryPresenter.getCategory(progressDialog);
        return view;
    }

    public void init(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),Constants.CATEGORY_SPAN_COUNT));
        adapter = new CategoryAdapter(arrCategory);
        recyclerView.setAdapter(adapter);
    }

    public void initPresenter(){
        MyApplication application = (MyApplication)getActivity().getApplication();
        application.getmAppComponent().inject(this);
        categoryPresenter.attachView(this);

    }

    @Override
    public void showProgress(ProgressDialog dialog) {
        dialog.show();
    }

    @Override
    public void hideProgress(ProgressDialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void showCategory(ArrayList<Category> categories) {
        arrCategory.addAll(categories);
        adapter.notifyDataSetChanged();
    }
}
