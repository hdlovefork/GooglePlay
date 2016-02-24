package com.dean.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.view.WebLayout;

import org.xutils.x;

/**
 * Created by Administrator on 2016/1/10.
 */
public abstract class BaseFragment<ServerResultType> extends Fragment {

    private static final String TAG = "BaseFragment";
    protected WebLayout mContentView = null;

    public BaseFragment(){
        mContentView = new WebLayout<ServerResultType>(x.app()) {
            @Override
            public View createPagerSuccess(ServerResultType serverResult) {
                return BaseFragment.this.createPagerSuccess(serverResult);
            }

            @Override
            public ServerResultType fetchDataFromServer() {
                return BaseFragment.this.fetchDataFromServer();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mContentView;
    }

    /**
     * 该方法在子线程中运行，从服务器获取数据，成功返回非空object，否则返回null
     *
     * @return
     */
    protected abstract ServerResultType fetchDataFromServer();

    /**
     * 该方法在UI线程中运行，返回一个成功后的页面
     *
     * @return
     */
    protected abstract View createPagerSuccess(ServerResultType serverResult);

    public void show() {
        if (mContentView != null) {
            mContentView.show();
        }
    }
}
