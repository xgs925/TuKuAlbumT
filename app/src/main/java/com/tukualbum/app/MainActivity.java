package com.tukualbum.app;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tukualbum.app.common.BaseActivity;
import com.tukualbum.tukualbum.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final int PERMISSION_STORAGE_ALBUM = 1;
    private static final int PERMISSION_STORAGE_IMAGE = 2;
    private static final int PERMISSION_STORAGE_VIDEO = 3;
    @BindView(R.id.vp_container)
    ViewPager mViewPager;
    @BindView(R.id.tb_title)
    TabLayout mTabLayout;
    private String[] mTitles = new String[]{"最近", "相册", "在线"};
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());



        for (int i = 0; i < mTitles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles[i]));
        }
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

//        requestPermission(PERMISSION_STORAGE_ALBUM);
    }

//    /**
//     * Scan, but unknown permissions.
//     *
//     * @param requestCode request code.
//     */
//    private void requestPermission(int requestCode) {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] permission = new String[]{
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            };
//            String[] deniedPermissions = PermissionUtils.getDeniedPermissions(this, permission);
//
//            if (deniedPermissions.length == 0) {
//                dispatchGrantedPermission(requestCode);
//            } else {
//                ActivityCompat.requestPermissions(
//                        this,
//                        deniedPermissions,
//                        requestCode);
//            }
//        } else {
//            dispatchGrantedPermission(requestCode);
//        }
//    }
//
//    /**
//     * Dispatch granted permission.
//     */
//    private void dispatchGrantedPermission(int requestCode) {
//        switch (requestCode) {
//            case PERMISSION_STORAGE_ALBUM:
//            case PERMISSION_STORAGE_IMAGE:
//            case PERMISSION_STORAGE_VIDEO: {
//                mViewPager.setAdapter(mSectionsPagerAdapter);
//                break;
//            }
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_STORAGE_ALBUM:
//            case PERMISSION_STORAGE_IMAGE:
//            case PERMISSION_STORAGE_VIDEO: {
//                if (PermissionUtils.isGrantedResult(grantResults)) dispatchGrantedPermission(requestCode);
//                else albumPermissionDenied();
//                break;
//            }
//        }
//    }

//    /**
//     * The permission for Album is denied.
//     */
//    private void albumPermissionDenied() {
//        new AlertDialog.Builder(this)
//                .setCancelable(false)
//                .setTitle(com.yanzhenjie.album.R.string.album_title_permission_failed)
//                .setMessage(com.yanzhenjie.album.R.string.album_permission_storage_failed_hint)
//                .setPositiveButton(com.yanzhenjie.album.R.string.album_dialog_sure, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new ImageFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
