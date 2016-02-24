package com.dean.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dean.googleplay.domain.StringList;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.http.RankingRequestParams;
import com.dean.googleplay.utils.DrawableUtils;
import com.dean.googleplay.view.FlowLayout;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.Random;

/**
 * 排行榜
 */
public class RankingFragment extends BaseFragment<StringList> {

    private final RankingRequestParams mParams;

    public RankingFragment() {
        mParams = new RankingRequestParams();
    }

    @Override
    protected StringList fetchDataFromServer() {
        return Http.getSync(mParams, StringList.class);
    }

    @Override
    protected View createPagerSuccess(StringList serverResult) {
        ScrollView scrollView = new ScrollView(x.app());
        FlowLayout flowLayout = new FlowLayout(x.app());
        scrollView.addView(flowLayout);
        flowLayout.setPadding(DensityUtil.dip2px(13), DensityUtil.dip2px(13), DensityUtil
                .dip2px(13), DensityUtil.dip2px(13));

        flowLayout.setHorizontalSpacing(DensityUtil.dip2px(13));
        flowLayout.setVerticalSpacing(DensityUtil.dip2px(13));

        Random random = new Random();
        for (int i = 0; i < serverResult.size(); i++) {

            TextView textView = new TextView(x.app());
            // 设置字体颜色
            textView.setTextColor(Color.WHITE);

            int red = 30 + random.nextInt(200);
            int green = 30 + random.nextInt(200);
            int blue = 30 + random.nextInt(200);
            // 合成一个颜色
            int color = Color.rgb(red, green, blue);
            // 设置字体居中
            textView.setGravity(Gravity.CENTER);

            // 设置字体大小
            // textView.setTextSize(16);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            // 设置四周的间距
            textView.setPadding(DensityUtil.dip2px(5), DensityUtil.dip2px(5),
                    DensityUtil.dip2px(5), DensityUtil.dip2px(5));

            GradientDrawable drawable = DrawableUtils.createDrawable(color,
                    color, 5);
            // 设置背景颜色
            textView.setBackgroundDrawable(drawable);

            // 设置文字
            textView.setText(serverResult.get(i));

            flowLayout.addView(textView);
        }
        return scrollView;
    }
}
