package com.dean.googleplay.viewholder;

import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.domain.DownloadInfo;
import com.dean.googleplay.domain.DownloadInfo.DownloadStatus;
import com.dean.googleplay.http.DownloadManager;
import com.dean.googleplay.http.MyImgOpt;
import com.dean.googleplay.recyclerview.WrapRecyclerView;
import com.dean.googleplay.view.ProgressArc;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 首页、应用栏目使用的ViewHolder
 */
public class AppViewHolder extends WrapRecyclerView.ViewHolder<AppList.AppInfo> implements View.OnClickListener {
    @ViewInject(R.id.item_rating)
    public RatingBar item_rating;
    @ViewInject(R.id.item_icon)
    public ImageView item_icon;
    @ViewInject(R.id.item_title)
    public TextView item_title;
    @ViewInject(R.id.item_size)
    public TextView item_size;
    @ViewInject(R.id.item_bottom)
    public TextView item_bottom;
    @ViewInject(R.id.action_progress)
    public FrameLayout mProgressLayout;
    @ViewInject(R.id.item_action)
    public RelativeLayout mActionLayout;
    @ViewInject(R.id.action_txt)
    public TextView mActionText;

    private ProgressArc mProgressArc;

    private DownloadStatus mState;
    private float mProgress;


    public AppViewHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View onCreateView() {
        mProgressArc = new ProgressArc(x.app());
        return LayoutInflater.from(getParent().getContext())
                             .inflate(R.layout.item_app_list, getParent(), false);
    }

    @Override
    public void initView() {
        int arcDiameter = DensityUtil.dip2px(26);
        //设置圆的直径
        mProgressArc.setArcDiameter(arcDiameter);
        //设置进度条的颜色
        mProgressArc.setProgressColor(x.app().getResources().getColor(R.color.progress));
        int size = DensityUtil.dip2px(27);
        //进度条的宽高
        mProgressLayout.addView(mProgressArc, new ViewGroup.LayoutParams(-2, -2));
        mActionLayout.setOnClickListener(this);
    }

    @Override
    public void onUpdateView() {
        AppList.AppInfo data = getData();
        if (data == null) {
            return;
        }
        item_rating.setRating(data.getStars());
        item_title.setText(data.getName());
        item_bottom.setText(data.getDes());
        item_size.setText(Formatter.formatFileSize(x.app(), data.getSize()));
        x.image()
         .bind(item_icon, x.app().getString(R.string.server_addr) + "/image?name=" + data
                 .getIconUrl(), MyImgOpt
                 .build(R.drawable.ic_default));
        refreshState(DownloadManager.getInstance().getDownloadInfo(data));
    }

    public void refreshState(DownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            mState = DownloadStatus.NONE;//还未开始下载
            mProgress = 0;
        } else {
            mState = downloadInfo.getDownloadStatus();
            mProgress = downloadInfo.getProgress();
        }
        switch (mState) {
            case NONE:
                mProgressArc.setForegroundResource(R.drawable.ic_download);
                //是否画进度条，不画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_download);
                break;
            case PAUSE:
                mProgressArc.setForegroundResource(R.drawable.ic_resume);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_paused);
                break;
            case ERROR:
                mProgressArc.setForegroundResource(R.drawable.ic_redownload);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_error);
                break;
            case WAITING:
                mProgressArc.setForegroundResource(R.drawable.ic_pause);
                //是否画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                mProgressArc.setProgress(mProgress, false);
                mActionText.setText(R.string.app_state_waiting);
                break;
            case DOWNLOADING:
                mProgressArc.setForegroundResource(R.drawable.ic_pause);
                //画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                mProgressArc.setProgress(mProgress, true);
                mActionText.setText((int) (mProgress * 100) + "%");
                break;
            case DOWNLOADED:
                mProgressArc.setForegroundResource(R.drawable.ic_install);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        //        RecyclerView parent = (RecyclerView) getParent();
        //        int position=parent.getChildAdapterPosition(getView());
        int position = getViewHolder().getAdapterPosition();
        DownloadManager downloadManager = DownloadManager.getInstance();
        if (v.getId() == R.id.item_action) {
            if (mState == DownloadStatus.NONE || mState == DownloadStatus.PAUSE
                    || mState == DownloadStatus.ERROR) {
                DownloadInfo downloadInfo = downloadManager.startDownload(getData(), position);
                //downloadInfo.setDownloadListener(this);
            } else if (mState == DownloadStatus.WAITING || mState == DownloadStatus.DOWNLOADING) {
                downloadManager.pauseDownload(getData());
            } else if (mState == DownloadStatus.DOWNLOADED) {
                downloadManager.install(getData());
            }
        }
    }


}
