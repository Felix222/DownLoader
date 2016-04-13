package com.felixpc.down.downloader.View;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Felix on 2016/4/13.
 */
public abstract class BaseActivity extends FragmentActivity {
    public String TAG = this.getClass().getName() + "  ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initContentView(savedInstanceState);
        initView();
    }

    public abstract void initContentView(Bundle savedInstanceState);

    public abstract void initView();

}
