package com.dean.googleplay.fragment;

import com.dean.googleplay.domain.DownloadInfo;
import com.dean.googleplay.http.DownloadManager;
import com.dean.googleplay.recyclerview.WrapRecyclerView;

/**
 * Created by Administrator on 2016/2/19.
 */
public abstract class AppListFragment<ServerResultType> extends BaseFragment<ServerResultType> implements DownloadInfo.OnDownloadListener {
    protected WrapRecyclerView mWrapRecyclerView;


    @Override
    public void onResume() {
        super.onResume();
        DownloadManager.getInstance().addOnDownloadListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        DownloadManager.getInstance().removeOnDownloadListener(this);
    }

    @Override
    public void onStatusChanged(DownloadInfo downloadInfo) {
        if (mWrapRecyclerView != null) {
            mWrapRecyclerView.getAdapter().notifyItemChanged(downloadInfo.getPosition());
        }
    }

    @Override
    public void onProgress(DownloadInfo downloadInfo) {
        if (mWrapRecyclerView != null) {
            mWrapRecyclerView.getAdapter().notifyItemChanged(downloadInfo.getPosition());
        }
    }
}
