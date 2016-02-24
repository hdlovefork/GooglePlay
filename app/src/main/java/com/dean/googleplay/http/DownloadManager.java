package com.dean.googleplay.http;


import android.content.Intent;
import android.net.Uri;

import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.domain.DownloadInfo;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/2/18.
 */
public class DownloadManager implements DownloadInfo.OnDownloadListener {

    public DownloadManager() {
        mDownLoadInfoMap = new ConcurrentHashMap<>();
    }

    private static DownloadManager mDownLoadManager;

    private List<DownloadInfo.OnDownloadListener> mOnDownloadListeners = new ArrayList<>();

    private ConcurrentHashMap<Integer, DownloadInfo> mDownLoadInfoMap;

    public synchronized static DownloadManager getInstance() {
        if (mDownLoadManager == null) {
            mDownLoadManager = new DownloadManager();
        }
        return mDownLoadManager;
    }

    /**
     * 注册观察者
     */
    public void addOnDownloadListener(DownloadInfo.OnDownloadListener listener) {
        synchronized (mOnDownloadListeners) {
            if (!mOnDownloadListeners.contains(listener)) {
                mOnDownloadListeners.add(listener);
            }
        }
    }

    /**
     * 反注册观察者
     */
    public void removeOnDownloadListener(DownloadInfo.OnDownloadListener listener) {
        synchronized (mOnDownloadListeners) {
            if (mOnDownloadListeners.contains(listener)) {
                mOnDownloadListeners.remove(listener);
            }
        }
    }

    public DownloadInfo getDownloadInfo(AppList.AppInfo appInfo) {
        if (appInfo == null) {
            return null;
        }
        int id = appInfo.getId();
        if (mDownLoadInfoMap.containsKey(id)) {
            return mDownLoadInfoMap.get(id);
        }
        return null;
    }

    public synchronized DownloadInfo startDownload(AppList.AppInfo appInfo, int position) {
        if (appInfo == null || position < 0) {
            return null;
        }
        int id = appInfo.getId();
        DownloadInfo downloadInfo = getDownloadInfo(appInfo);
        if (downloadInfo == null) {
            downloadInfo = new DownloadInfo(appInfo, position);
            mDownLoadInfoMap.putIfAbsent(id, downloadInfo);
        }
        DownloadInfo.DownloadStatus downloadStatus = downloadInfo.getDownloadStatus();
        // 判断状态是否为STATE_NONE、STATE_PAUSED、STATE_ERROR。只有这3种状态才能进行下载，其他状态不予处理
        if (downloadStatus == DownloadInfo.DownloadStatus.NONE ||
                downloadStatus == DownloadInfo.DownloadStatus.ERROR ||
                downloadStatus == DownloadInfo.DownloadStatus.PAUSE) {
            Callback.Cancelable cancelable = x.http().get(new DownloadRequestParams(appInfo
                    .getDownloadUrl()), downloadInfo);
            downloadInfo.setCancelable(cancelable);
            downloadInfo.setDownloadListener(this);
        }
        return downloadInfo;
    }

    public void stopDownload(AppList.AppInfo appInfo) {
        int id = appInfo.getId();
        if (mDownLoadInfoMap.containsKey(id)) {
            DownloadInfo downloadInfo = mDownLoadInfoMap.get(id);
            downloadInfo.cancel();
        }
    }

    public void pauseDownload(AppList.AppInfo appInfo) {
        int id = appInfo.getId();
        if (mDownLoadInfoMap.containsKey(id)) {
            DownloadInfo downloadInfo = mDownLoadInfoMap.get(id);
            downloadInfo.pause();
        }
    }

    public void install(AppList.AppInfo appInfo) {
        stopDownload(appInfo);
        DownloadInfo info = getDownloadInfo(appInfo);// 找出下载信息
        if (info != null) {// 发送安装的意图
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.parse("file://" + info.getFile().getPath()),
                    "application/vnd.android.package-archive");
            x.app().startActivity(installIntent);
        }
    }


    @Override
    public void onStatusChanged(DownloadInfo downloadInfo) {
        synchronized (mOnDownloadListeners) {
            for (DownloadInfo.OnDownloadListener listener : mOnDownloadListeners) {
                listener.onStatusChanged(downloadInfo);
            }
        }
    }

    @Override
    public void onProgress(DownloadInfo downloadInfo) {
        synchronized (mOnDownloadListeners) {
            for (DownloadInfo.OnDownloadListener listener : mOnDownloadListeners) {
                listener.onProgress(downloadInfo);
            }
        }
    }
}
