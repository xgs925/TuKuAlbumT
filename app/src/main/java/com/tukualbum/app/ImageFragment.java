package com.tukualbum.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukualbum.app.entity.Image;
import com.tukualbum.tukualbum.R;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumFolder;
import com.yanzhenjie.album.Filter;
import com.yanzhenjie.album.task.MediaReadTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gus on 2018/4/19.
 */

public class ImageFragment extends Fragment {
    private ArrayList<AlbumFile> mCheckedList = new ArrayList<>();
    private Filter<Long> mSizeFilter;
    private Filter<String> mMimeFilter;
    private Filter<Long> mDurationFilter;
    private boolean mFilterVisibility;
    List<AlbumFile> images = new ArrayList<>();
    @BindView(R.id.rv_img)
    RecyclerView mRecyclerView;
    ImageListAdapter imageListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MediaReadTask scanTask = new MediaReadTask(getContext(), Album.FUNCTION_CHOICE_IMAGE, new MediaReadTask.Callback() {
            @Override
            public void onScanCallback(ArrayList<AlbumFolder> folders) {
                ArrayList<AlbumFile> albumFiles = folders.get(0).getAlbumFiles();

                images.addAll(albumFiles);
                imageListAdapter.notifyItemRangeInserted(images.size(),albumFiles.size());
            }
        }, new ArrayList<AlbumFile>(), mSizeFilter, mMimeFilter, mDurationFilter, mFilterVisibility);
        scanTask.execute(new ArrayList<AlbumFile>());
    }

    private void initRecyclerView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                layoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
//            }
//        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        imageListAdapter = new ImageListAdapter(getContext(), images);

        mRecyclerView.setAdapter(imageListAdapter);
    }


}
