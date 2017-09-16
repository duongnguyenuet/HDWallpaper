package com.binary.hdwallpaper.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.binary.hdwallpaper.R;
import com.binary.hdwallpaper.models.Image;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duong on 9/8/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ImageViewHolder> {
    private ArrayList<Image> images;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MainAdapter(ArrayList<Image> images) {
        this.images = images;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = images.get(position);
        Glide.with(context)
                .load(image.getWallpagerImageThumb())
                .placeholder(android.R.drawable.screen_background_light)
                .error(android.R.drawable.screen_background_dark)
                .centerCrop()
                .into(holder.ivImage);
        holder.tvId.setText("Id : " + image.getId());
        holder.tvCatId.setText("Cat id : " + image.getCatId());
        holder.tvCategoryName.setText("Cat name : " + image.getCategoryName());
        holder.tvTotalViews.setText("Total views : " + image.getTotalViews());
        Log.e("xxx","xxx");
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvId)
        TextView tvId;
        @BindView(R.id.tvCatId)
        TextView tvCatId;
        @BindView(R.id.tvTotalViews)
        TextView tvTotalViews;
        @BindView(R.id.tvCategoryName)
        TextView tvCategoryName;


        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(view, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(onItemLongClickListener!=null){
                onItemLongClickListener.onLongClick(view,getPosition());
            }
            return false;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(View view, int position);
    }
}
