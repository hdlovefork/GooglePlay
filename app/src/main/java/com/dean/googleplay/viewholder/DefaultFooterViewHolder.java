package com.dean.googleplay.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.recyclerview.WrapRecyclerView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 默认加载更多页脚
 */
public class DefaultFooterViewHolder extends WrapRecyclerView.ViewHolder<Integer> {
    //表示有更多的数据类型
    public static final int HAS_MORE = 1;
    //表示没有更多的数据
    public static final int NO_MORE = 2;
    //表示加载失败
    public static final int ERROR = 3;

    @ViewInject(R.id.rl_more_loading)
    public RelativeLayout rlLoading;
    @ViewInject(R.id.rl_more_error)
    public RelativeLayout rlError;
    @ViewInject(R.id.loading_error_txt)
    public TextView tvErrorText;

    public DefaultFooterViewHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View onCreateView() {
        return LayoutInflater.from(getParent().getContext())
                             .inflate(R.layout.item_more, getParent(), false);
    }

    @Override
    public void initView() {
        setData(HAS_MORE);
    }

    @Override
    public void onUpdateView() {
        Integer data = getData();
        rlLoading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
        rlError.setVisibility(data == ERROR || data == NO_MORE ? View.VISIBLE : View.GONE);
        if (data == NO_MORE) {
            tvErrorText.setText(x.app().getString(R.string.no_more));
        } else if (data == ERROR) {
            tvErrorText.setText(x.app().getString(R.string.load_error));
        } else {
            tvErrorText.setText(x.app().getString(R.string.load_more));
        }
    }
}
