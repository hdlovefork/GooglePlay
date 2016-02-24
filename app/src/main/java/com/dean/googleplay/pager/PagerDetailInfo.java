package com.dean.googleplay.pager;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.http.MyImgOpt;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 应用详情信息页面
 */
public class PagerDetailInfo extends PagerView<AppList.AppInfo> {

    @ViewInject(R.id.item_icon)
    private ImageView item_icon;
    @ViewInject(R.id.item_title)
    private TextView item_title;
    @ViewInject(R.id.item_rating)
    private RatingBar item_rating;
    @ViewInject(R.id.item_date)
    private TextView item_date;
    @ViewInject(R.id.item_size)
    private TextView item_size;
    @ViewInject(R.id.item_version)
    private TextView item_version;
    @ViewInject(R.id.item_download)
    private TextView item_download;

    public PagerDetailInfo(AppList.AppInfo data) {
        super(data);
    }


    @Override
    public View onCreateView() {
        return View.inflate(x.app(), R.layout.app_detail_info, null);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onUpdateView() {
        AppList.AppInfo appInfo = getData();
        x.image()
         .bind(item_icon, x.app().getString(R.string.server_addr) + "/image?name=" + appInfo
                 .getIconUrl(), MyImgOpt
                 .build(R.drawable.ic_default));
        item_title.setText(appInfo.getName());
        item_rating.setRating(appInfo.getStars());
        item_size.setText(Formatter.formatFileSize(x.app(), appInfo.getSize()));
        item_date.setText(appInfo.getDate());
        item_version.setText(appInfo.getVersion());
        item_download.setText(appInfo.getDownloadNum());
    }
}
