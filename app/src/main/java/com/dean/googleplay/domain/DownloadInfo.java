package com.dean.googleplay.domain;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/2/18.
 */
public class DownloadInfo implements Callback.ProgressCallback<File> {

    private Cancelable mCancelable;


    private File mFile;
    private float mProgress;


    private int mPosition;

    /**
     *
     * @param appInfo 待下载的APP对象
     * @param position 该APP在Adapter列表中的位置
     */
    public DownloadInfo(AppList.AppInfo appInfo, int position) {
        mAppInfo = appInfo;
        mPosition = position;
        mDownloadStatus = DownloadStatus.NONE;
    }

    public File getFile() {
        return mFile;
    }

    public AppList.AppInfo getAppInfo() {
        return mAppInfo;
    }

    private AppList.AppInfo mAppInfo;

    private DownloadStatus mDownloadStatus = DownloadStatus.NONE;

    @Override
    public void onWaiting() {
        mDownloadStatus = DownloadStatus.WAITING;
        if (mDownloadListener != null) {
            mDownloadListener.onStatusChanged(this);
        }
    }

    @Override
    public void onStarted() {
        mDownloadStatus = DownloadStatus.DOWNLOADING;
        if (mDownloadListener != null) {
            mDownloadListener.onStatusChanged(this);
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        if(!isDownloading) return;
        mProgress = (float) current / total;
        if (mDownloadListener != null) {
            mDownloadListener.onProgress(this);
        }
    }

    @Override
    public void onSuccess(File result) {
        mDownloadStatus = DownloadStatus.DOWNLOADED;
        mFile = result;
        if (mDownloadListener != null) {
            mDownloadListener.onStatusChanged(this);
        }
        LogUtil.d("下载成功：" + mFile.toString());
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        mDownloadStatus = DownloadStatus.ERROR;
        if (mDownloadListener != null) {
            mDownloadListener.onStatusChanged(this);
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
    }

    @Override
    public void onFinished() {

    }

    public void setCancelable(Cancelable cancelable) {
        mCancelable = cancelable;
    }

    public void cancel() {
        mDownloadStatus = DownloadStatus.NONE;
        if (mCancelable != null && !mCancelable.isCancelled()) {
            mCancelable.cancel();
        }
        if (mDownloadListener != null) {
            mDownloadListener.onStatusChanged(this);
        }
    }

    public void pause() {
        mDownloadStatus = DownloadStatus.PAUSE;
        if (mCancelable != null && !mCancelable.isCancelled()) {
            mCancelable.cancel();
        }
        if (mDownloadListener != null) {
            mDownloadListener.onStatusChanged(this);
        }
    }

    public DownloadStatus getDownloadStatus() {
        return mDownloadStatus;
    }

    public void setDownloadListener(OnDownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    public float getProgress() {
        return mProgress;
    }

    /**
     * 获取该下载信息在Adapter列表中的位置
     * @return
     */
    public int getPosition() {
        return mPosition;
    }


    public enum DownloadStatus {
        NONE, WAITING, DOWNLOADING, DOWNLOADED, ERROR, PAUSE;
    }

    private OnDownloadListener mDownloadListener;

    public interface OnDownloadListener {
        void onStatusChanged(DownloadInfo downloadInfo);

        void onProgress(DownloadInfo downloadInfo);
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "mAppInfo=" + mAppInfo +
                '}';
    }
}
