package com.dean.googleplay.pager;

import android.view.View;
import android.widget.ImageView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.http.MyImgOpt;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 应用截图详情页面
 */
public class PagerDetailScreen extends PagerView<AppList.AppInfo> {
    @ViewInject(R.id.screen_1)
    ImageView screen_1;
    @ViewInject(R.id.screen_2)
    ImageView screen_2;
    @ViewInject(R.id.screen_3)
    ImageView screen_3;
    @ViewInject(R.id.screen_4)
    ImageView screen_4;
    @ViewInject(R.id.screen_5)
    ImageView screen_5;

    ImageView[] iv_screen_Array;

    public PagerDetailScreen(AppList.AppInfo data) {
        super(data);
    }

    @Override
    public View onCreateView() {
        return View.inflate(x.app(), R.layout.app_detail_screen, null);
    }

    @Override
    public void initView() {
        iv_screen_Array = new ImageView[5];
        iv_screen_Array[0] = screen_1;
        iv_screen_Array[1] = screen_2;
        iv_screen_Array[2] = screen_3;
        iv_screen_Array[3] = screen_4;
        iv_screen_Array[4] = screen_5;
    }


    @Override
    public void onUpdateView() {
        AppList.AppInfo data = getData();
        if (data.getScreen() != null) {
            for (int i = 0; i < data.getScreen().size(); i++) {
                String imgUrl=data.getScreen().get(i);
                x.image().bind(iv_screen_Array[i], x.app()
                                                    .getString(R.string.server_addr) + "/image?name=" +imgUrl
                        , MyImgOpt
                        .build(R.drawable.ic_default));
            }
        }
    }
}
