package com.dean.googleplay.pager;

import android.view.View;

import com.dean.googleplay.recyclerview.ViewDataBind;

import org.xutils.x;

/**
 * Created by Administrator on 2016/2/1.
 */
public abstract class PagerView<BindDataType> implements ViewDataBind<BindDataType> {

    public PagerView() {
        this(null);
    }

    public PagerView(BindDataType data) {
        mData = data;
        mView = onCreateView();
        x.view().inject(this, mView);
        initView();
    }


    public View getView() {
        if (mData != null) {
            onUpdateView();
        }
        return mView;
    }

    protected View mView;
    protected BindDataType mData;

    public BindDataType getData() {
        return mData;
    }

    public void setData(BindDataType data) {
        mData = data;
    }

    /**
     * 创建视图
     * @return
     */
    public abstract View onCreateView();

    /**
     * 更新视图界面数据
     */
    public abstract void onUpdateView();
}
