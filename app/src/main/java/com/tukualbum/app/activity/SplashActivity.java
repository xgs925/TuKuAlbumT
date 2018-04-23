package com.tukualbum.app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.tukualbum.app.util.PermissionUtil;
import com.tukualbum.tukualbum.R;

import cn.hx.permissionsetting.PermSetting;

public class SplashActivity extends AppCompatActivity {
    private final int CODE_EXTERNAL_STORAGE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PermissionUtil.isStoragePermissionsGranted(this)) {
            int launchMode = PermSetting.gotoPermSetting(SplashActivity.this, 1);
        } else {
            PermissionUtil.requestPermissions(this, CODE_EXTERNAL_STORAGE_PERMISSIONS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE_EXTERNAL_STORAGE_PERMISSIONS:
                boolean gotPermission = grantResults.length > 0;

                for (int result : grantResults) {
                    gotPermission &= result == PackageManager.PERMISSION_GRANTED;
                }

                if (gotPermission) {

                } else {
                    int launchMode = PermSetting.gotoPermSetting(SplashActivity.this, 1);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
