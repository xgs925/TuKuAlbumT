package com.tukualbum.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.tukualbum.app.entity.Image;
import com.tukualbum.app.view.DynamicHeightImageView;
import com.tukualbum.tukualbum.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gus on 2018/4/19.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    private List<Image> mList;
    private Context mContext;
    private HashMap imageRMap = new HashMap();


    public ImageListAdapter(Context context, List<Image> imageList) {
        mList = imageList;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Image image = mList.get(position);
        viewHolder.image = image;
        if (imageRMap.containsKey(position)) {
            viewHolder.imageView.setRatio((Float) imageRMap.get(position));
        }
        Glide.with(mContext).load(image.url)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)


                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (!imageRMap.containsKey(position)) {
                            float r = (resource.getHeight() * 1.0f) / resource.getWidth();
                            imageRMap.put(position, r);
                            viewHolder.imageView.setRatio(r);
                        }
                        viewHolder.imageView.setImageBitmap(resource);
                    }
                });
    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_img)
        DynamicHeightImageView imageView;
        Image image;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {
        }
    }
}
