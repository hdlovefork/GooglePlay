package com.dean.googleplay.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.dean.googleplay.R;

import org.xutils.x;

/**
 * Created by Administrator on 2016/1/10.
 */
public class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActionBar = getSupportActionBar();
        int flags = mActionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_HOME;
        mActionBar.setDisplayOptions(flags);
        mActionBar.setIcon(R.mipmap.ic_launcher);
    }
}
