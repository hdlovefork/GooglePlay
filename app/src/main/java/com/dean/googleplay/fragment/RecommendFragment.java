package com.dean.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.dean.googleplay.domain.StringList;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.http.RecommendRequestParams;
import com.dean.googleplay.view.StellarMap;

import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.Random;

/**
 * 推荐
 */
public class RecommendFragment extends BaseFragment<StringList> {

    private final RecommendRequestParams mParams;
    private StringList mDatas;

    public RecommendFragment() {
        mParams = new RecommendRequestParams();
    }

    @Override
    protected StringList fetchDataFromServer() {
        return Http.getSync(mParams, StringList.class);
    }

    @Override
    protected View createPagerSuccess(StringList serverResult) {
        mDatas = serverResult;
        StellarMap stellarMap = new StellarMap(x.app());
        // 设置四周的距离
        stellarMap.setInnerPadding(DensityUtil.dip2px(13), DensityUtil.dip2px(13),
                DensityUtil.dip2px(13), DensityUtil.dip2px(13));

        RecommendAdapter adapter = new RecommendAdapter();
        // 设置数据
        stellarMap.setAdapter(adapter);
        //设置几行几列
        stellarMap.setRegularity(6, 9);
        // 设置从哪一组数据开始
        /**
         * 第一个参数表示从第0组开始 第二个参数是否开启动画
         */
        stellarMap.setGroup(0, true);

        return stellarMap;
    }

    private class RecommendAdapter implements StellarMap.Adapter {

        private Random mRandom;

        public RecommendAdapter() {
            //随机一个颜色
            mRandom = new Random();
        }

        /**
         * 表示一共有多少组数据
         */
        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return 3;
        }

        /**
         * 每一组数据一共有多少个
         */
        @Override
        public int getCount(int group) {
            // TODO Auto-generated method stub
            return 11;
        }

        /**
         * 展示数据
         */
        @Override
        public View getView(int group, int position, View convertView) {
            //LogUtil.d(group + "," + position + "," + convertView);
            TextView textView = new TextView(x.app());

            //随机颜色
            int red = 30 + mRandom.nextInt(200);
            int green = 30 + mRandom.nextInt(200);
            int blue = 30 + mRandom.nextInt(200);

            //合成一种颜色
            int color = Color.rgb(red, green, blue);


            //设置字体颜色
            //随机颜色的时候需要注意。不要到255(白色)不要0(黑色)
            textView.setTextColor(color);
            //设置字体的大小
            textView.setTextSize(12 + mRandom.nextInt(16));

            //给文本控件设置数据
            textView.setText(mDatas.get(group * getCount(group) + position));

            return textView;
        }

        /**
         * 上一组数据
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            // TODO Auto-generated method stub
            LogUtil.d("上一组数据: " + group + "," + degree);
            return --group % getGroupCount();
        }

        /**
         * 下一组数据
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            // TODO Auto-generated method stub
            LogUtil.d("下一组数据：" + group + "," + isZoomIn);
            return ++group % getGroupCount();
        }

    }
}
