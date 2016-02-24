package com.dean.googleplay;

import android.app.Application;

import com.dean.googleplay.factory.FragmentFactory;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * Created by Administrator on 2016/1/10.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
