package com.dean.googleplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.activity.DetailActivity;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.recyclerview.WrapRecyclerView;

import java.util.List;

/**
 * 用于主页、应用、游戏的适配器
 */
public abstract class AppListAdapter<ItemDataType> extends AbsAdapter<ItemDataType> implements AbsAdapter.OnRecyclerViewItemClickListener {
    public AppListAdapter(List list) {
        super(list);
        setOnRecyclerViewItemClickListener(this);
    }

    @Override
    public void onItemClick(WrapRecyclerView.ViewHolder viewHolder, ViewGroup parent, View v) {
        AppList.AppInfo data = (AppList.AppInfo) viewHolder.getData();
        if (!(parent instanceof RecyclerView)) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) parent;
        //        int position = recyclerView.getChildAdapterPosition(v);
        int position = viewHolder == null || viewHolder.getViewHolder() == null ? -1 : viewHolder
                .getViewHolder().getAdapterPosition();
        if (data != null) {
            DetailActivity.startAction(parent.getContext(), data
                    .getPackageName(), position);
        }
    }
}
