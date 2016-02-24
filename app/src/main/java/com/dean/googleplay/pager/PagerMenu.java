package com.dean.googleplay.pager;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.UserInfo;
import com.dean.googleplay.http.UserInfoRequestParams;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 侧边栏菜单视图
 */
public class PagerMenu extends PagerView<UserInfo> implements View.OnClickListener {
    @ViewInject(R.id.home_layout)
    private RelativeLayout mHomeLayout;
    @ViewInject(R.id.setting_layout)
    private RelativeLayout mSettingLayout;
    @ViewInject(R.id.theme_layout)
    private RelativeLayout mThemeLayout;
    @ViewInject(R.id.scans_layout)
    private RelativeLayout mScansLayout;
    @ViewInject(R.id.feedback_layout)
    private RelativeLayout mFeedbackLayout;
    @ViewInject(R.id.updates_layout)
    private RelativeLayout mUpdatesLayout;
    @ViewInject(R.id.about_layout)
    private RelativeLayout mAboutLayout;
    @ViewInject(R.id.exit_layout)
    private RelativeLayout mExitLayout;
    @ViewInject(R.id.photo_layout)
    private RelativeLayout mPhotoLayout;

    @ViewInject(R.id.image_photo)
    private ImageView mPhoto;
    @ViewInject(R.id.user_name)
    private TextView mTvUserName;
    @ViewInject(R.id.user_email)
    private TextView mTvUserEmail;


    @Override
    public View onCreateView() {
        return View.inflate(x.app(),R.layout.menu_list,null);
    }

    @Override
    public void initView() {
        mHomeLayout.setOnClickListener(this);
        mSettingLayout.setOnClickListener(this);
        mThemeLayout.setOnClickListener(this);
        mScansLayout.setOnClickListener(this);
        mFeedbackLayout.setOnClickListener(this);
        mUpdatesLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);
        mExitLayout.setOnClickListener(this);
        mPhotoLayout.setOnClickListener(this);
    }

    @Override
    public void onUpdateView() {
        if (mData != null) {
            x.image().bind(mPhoto, x.app().getString(R.string.server_addr) + "/image?name=" + mData
                    .getUrl());
            mTvUserName.setText(mData.getName());
            mTvUserEmail.setText(mData.getEmail());
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_layout:
                Toast.makeText(x.app(), R.string.tv_home, Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_layout:
                Toast.makeText(x.app(), R.string.tv_setting, Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_layout:
                Toast.makeText(x.app(), R.string.tv_theme, Toast.LENGTH_SHORT).show();
                break;
            case R.id.scans_layout:
                Toast.makeText(x.app(), R.string.tv_scans, Toast.LENGTH_SHORT).show();
                break;
            case R.id.feedback_layout:
                Toast.makeText(x.app(), R.string.tv_feedback, Toast.LENGTH_SHORT).show();
                break;
            case R.id.updates_layout:
                Toast.makeText(x.app(), R.string.tv_updates, Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_layout:
                Toast.makeText(x.app(), R.string.tv_about, Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_layout:
                Toast.makeText(x.app(), R.string.tv_exit, Toast.LENGTH_SHORT).show();
                break;
            case R.id.photo_layout:
                x.http().get(new UserInfoRequestParams(), new Callback.CommonCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo result) {
                        setData(result);
                        onUpdateView();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
        }
    }
}
