package com.dean.googleplay.pager;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.domain.DownloadInfo;
import com.dean.googleplay.domain.DownloadInfo.DownloadStatus;
import com.dean.googleplay.http.DownloadManager;
import com.dean.googleplay.view.ProgressHorizontal;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

/**
 * Created by Administrator on 2016/2/20.
 */
public class PagerDetailBottom extends PagerView<AppList.AppInfo> implements View.OnClickListener, DownloadInfo.OnDownloadListener {
    private Button mBtnFavorites, mBtnShare, mBtnProgress;
    private FrameLayout mLayout;
    private ProgressHorizontal mProgressView;
    private float mProgress;
    private DownloadManager mDownloadManager;
    private DownloadStatus mState;
    private int mPosition;//该App对应RecyclerView项的Position

    public PagerDetailBottom(AppList.AppInfo data, int position) {
        super(data);
        mPosition = position;
        mDownloadManager = DownloadManager.getInstance();
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(x.app(), R.layout.app_detail_bottom, null);
        mBtnFavorites = (Button) view.findViewById(R.id.bottom_favorites);
        mBtnShare = (Button) view.findViewById(R.id.bottom_share);
        mBtnProgress = (Button) view.findViewById(R.id.progress_btn);
        mBtnFavorites.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mBtnProgress.setOnClickListener(this);
        mBtnFavorites.setText(R.string.bottom_favorites);
        mBtnShare.setText(R.string.bottom_share);

        mLayout = (FrameLayout) view.findViewById(R.id.progress_layout);
        mProgressView = new ProgressHorizontal(x.app());
        mProgressView.setId(R.id.detail_progress);
        mProgressView.setOnClickListener(this);
        mProgressView.setProgressTextVisible(true);
        mProgressView.setProgressTextColor(Color.WHITE);
        mProgressView.setProgressTextSize(DensityUtil.dip2px(18));
        mProgressView.setBackgroundResource(R.drawable.progress_bg);
        mProgressView.setProgressResource(R.drawable.progress);

        mLayout.addView(mProgressView);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onUpdateView() {
        refreshState(mDownloadManager.getDownloadInfo(getData()));
    }

    public void startObserver() {
        mDownloadManager.addOnDownloadListener(this);
    }

    public void stopObserver() {
        mDownloadManager.removeOnDownloadListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_favorites:
                //UIUtils.showToastSafe(R.string.bottom_favorites);
                break;
            case R.id.bottom_share:
                //UIUtils.showToastSafe(R.string.bottom_share);
                break;
            case R.id.progress_btn:
            case R.id.detail_progress:
                if (mState == DownloadStatus.NONE || mState == DownloadStatus.PAUSE || mState == DownloadStatus.ERROR) {
                    mDownloadManager.startDownload(getData(), mPosition);
                } else if (mState == DownloadStatus.WAITING || mState == DownloadStatus.DOWNLOADING) {
                    mDownloadManager.pauseDownload(getData());
                } else if (mState == DownloadStatus.DOWNLOADED) {
                    mDownloadManager.install(getData());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStatusChanged(DownloadInfo downloadInfo) {
        refreshState(downloadInfo);
    }

    @Override
    public void onProgress(DownloadInfo downloadInfo) {
        refreshState(downloadInfo);
    }

    public void refreshState(DownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            mState = DownloadStatus.NONE;
            mProgress = 0;
        } else {
            if (downloadInfo.getAppInfo().getId() != getData().getId()) {
                return;
            }
            mState = downloadInfo.getDownloadStatus();
            mProgress = downloadInfo.getProgress();
        }
        switch (mState) {
            case NONE:
                mProgressView.setVisibility(View.GONE);
                mBtnProgress.setVisibility(View.VISIBLE);
                mBtnProgress.setText(x.app().getResources().getString(R.string.app_state_download));
                break;
            case PAUSE:
                mProgressView.setVisibility(View.VISIBLE);
                mProgressView.setProgress(mProgress);
                mProgressView
                        .setCenterText(x.app().getResources().getString(R.string.app_state_paused));
                mBtnProgress.setVisibility(View.GONE);
                break;
            case ERROR:
                mProgressView.setVisibility(View.GONE);
                mBtnProgress.setVisibility(View.VISIBLE);
                mBtnProgress.setText(R.string.app_state_error);
                break;
            case WAITING:
                mProgressView.setVisibility(View.VISIBLE);
                mProgressView.setProgress(mProgress);
                mProgressView.setCenterText(x.app().getResources()
                                             .getString(R.string.app_state_waiting));
                mBtnProgress.setVisibility(View.GONE);
                break;
            case DOWNLOADING:
                mProgressView.setVisibility(View.VISIBLE);
                mProgressView.setProgress(mProgress);
                mProgressView.setCenterText("");
                mBtnProgress.setVisibility(View.GONE);
                break;
            case DOWNLOADED:
                mProgressView.setVisibility(View.GONE);
                mBtnProgress.setVisibility(View.VISIBLE);
                mBtnProgress.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }
}
