package com.ximsfei.skindemo.window;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ximsfei.skindemo.BaseActivity;
import com.ximsfei.skindemo.R;

public class WindowManagerActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);
        initToolbar();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Log.d(TAG, "onClick:: Build.VERSION.SDK_INT >= 23");
                    if (!Settings.canDrawOverlays(WindowManagerActivity.this)) {
                        Log.d(TAG, "onClick:: request  canDrawOverlays ...");
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 10);
                        return;
                    }
                }
                startWindowService();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Settings.canDrawOverlays(this)) {
                Log.d(TAG, "onActivityResult:: canDrawOverlays ...");
                startWindowService();
            } else {
                Toast.makeText(WindowManagerActivity.this, "not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startWindowService() {
        Intent intent = new Intent();
        intent.setClass(WindowManagerActivity.this, WindowService.class);
        startService(intent);
    }
}
