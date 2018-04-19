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
import com.tukualbum.app.entity.Image;
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
    private HashMap imageHeightMap=new HashMap();


    public ImageListAdapter(Context context, List<Image> imageList) {
        mList = imageList;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Image image = mList.get(position);
        viewHolder.image = image;
        if (!imageHeightMap.containsKey(position)) {
            View view = holder.getView(R.id.item_beagirs_ig);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = (int) (TDevice.getScreenWidth() / 2);
            params.height = (int) (TDevice.getScreenHeight() / 2 + Math.random() * 100);
            view.setLayoutParams(params);
            imageHeightMap.put(position, params.height);
        } else {
            Integer integer = (Integer) imageHeightMap.get(position);
            View view = holder.getView(R.id.item_beagirs_ig);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (int) (TDevice.getScreenWidth() / 2);
            params.height = integer;
            view.setLayoutParams(params);
        }
        Glide.with(mContext).load(image.url)
                .asBitmap()
                .into(
                        new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                int height = resource.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                                resource.getWidth();
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
                                layoutParams.height = height;
                                viewHolder.imageView.setLayoutParams(layoutParams);
                                viewHolder.imageView.setImageBitmap(resource);
                            }
                        }
                );
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
        ImageView imageView;
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
