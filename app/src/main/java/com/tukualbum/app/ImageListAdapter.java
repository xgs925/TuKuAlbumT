package com.tukualbum.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.tukualbum.app.entity.Image;
import com.tukualbum.app.view.DynamicHeightImageView;
import com.tukualbum.tukualbum.R;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.task.DefaultAlbumLoader;
import com.yanzhenjie.album.util.DisplayUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gus on 2018/4/19.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    private List<AlbumFile> mList;
    private Context mContext;
    private HashMap imageRMap = new HashMap();


    public ImageListAdapter(Context context, List<AlbumFile> imageList) {
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
        final AlbumFile image = mList.get(position);
        viewHolder.image = image;

        DefaultAlbumLoader albumLoader= (DefaultAlbumLoader)Album.getAlbumConfig().getAlbumLoader();
        if (image.getRatio()!=0) {
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
            rlp.width=200;
            rlp.height = (int) (rlp.width * image.getRatio());
            viewHolder.imageView.setLayoutParams(rlp);
            viewHolder.imageView.setRatio(image.getRatio());
        }else {
            albumLoader.setCallback(new DefaultAlbumLoader.Callback() {
                @Override
                public void done(int h, int w) {
                    if (!imageRMap.containsKey(position)) {
                        float r = (h * 1.0f) / w;
                        image.setRatio(r);
                        imageRMap.put(position, r);

                        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
                        rlp.width=200;
                        rlp.height = (int) (rlp.width * r);
                        viewHolder.imageView.setLayoutParams(rlp);
                        viewHolder.imageView.setRatio(r);
                    }
                }
            });
        }
//        albumLoader.loadAlbumFile(viewHolder.imageView,image, 500,500);
        Glide.with(mContext).load("file://"+image.getPath()).into(viewHolder.imageView);
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
        AlbumFile image;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {
        }
    }
}
