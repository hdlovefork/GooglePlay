package com.dean.googleplay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.http.AppDetailRequestParams;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.pager.PagerDetailDesc;
import com.dean.googleplay.pager.PagerDetailInfo;
import com.dean.googleplay.pager.PagerDetailBottom;
import com.dean.googleplay.pager.PagerDetailSafe;
import com.dean.googleplay.pager.PagerDetailScreen;
import com.dean.googleplay.pager.PagerView;
import com.dean.googleplay.view.WebLayout;

import org.xutils.x;

/**
 * 应用信息详情页面
 */
public class DetailActivity extends BaseActivity {

    private PagerDetailBottom mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        final String packageName = getIntent().getStringExtra("packageName");
        final int position = getIntent().getIntExtra("position", -1);
        WebLayout contentView = new WebLayout<AppList.AppInfo>(x.app()) {

            @Override
            public View createPagerSuccess(AppList.AppInfo serverResult) {
                View container = View.inflate(x.app(), R.layout.activity_detail, null);
                //创建信息页面
                FrameLayout detail_info = (FrameLayout) container.findViewById(R.id.detail_info);
                PagerView infoView = new PagerDetailInfo(serverResult);
                detail_info.addView(infoView.getView());
                //创建安全下拉页面
                FrameLayout detail_safe = (FrameLayout) container.findViewById(R.id.detail_safe);
                PagerView safeView = new PagerDetailSafe(serverResult);
                detail_safe.addView(safeView.getView());
                //创建应用截图页面
                FrameLayout detail_screen = (FrameLayout) container
                        .findViewById(R.id.detail_screen);
                PagerView screenView = new PagerDetailScreen(serverResult);
                detail_screen.addView(screenView.getView());
                //创建应用描述页面
                FrameLayout detail_des = (FrameLayout) container.findViewById(R.id.detail_des);
                PagerView descView = new PagerDetailDesc(serverResult);
                detail_des.addView(descView.getView());

                // 底部区域
                FrameLayout bottom_layout = (FrameLayout) container
                        .findViewById(R.id.bottom_layout);
                mProgressView = new PagerDetailBottom(serverResult, position);
                bottom_layout.addView(mProgressView.getView());
                mProgressView.startObserver();
                return container;
            }

            @Override
            public AppList.AppInfo fetchDataFromServer() {
                return Http.getSync(new AppDetailRequestParams(packageName), AppList.AppInfo.class);
            }
        };
        contentView.show();
        setContentView(contentView);
    }

    /**
     * 顶部导航后退
     */
    private void initActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.app_detail);
        //应用图标来返回主页
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }

    public static void startAction(Context context, String packageName, int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("packageName", packageName);
        intent.putExtra("position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressView != null) {
            mProgressView.stopObserver();
        }
    }

    //ActionBar菜单项事件处理，这里针对按下后退按钮后返回主页
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //后退键被按下后返回主页
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
