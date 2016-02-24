package com.dean.googleplay.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.R;
import com.dean.googleplay.recyclerview.WrapRecyclerView;
import com.dean.googleplay.view.viewpager.AdvertImagePagerAdapter;
import com.dean.googleplay.view.viewpager.AutoScrollViewPager;
import com.dean.googleplay.view.viewpager.CirclePageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 幻灯片头部
 */
public class SlideHeaderViewHolder extends WrapRecyclerView.ViewHolder<List<String>> {

    @ViewInject(R.id.vp_pager)
    AutoScrollViewPager mViewPager;
    @ViewInject(R.id.indicator)
    CirclePageIndicator indicator;


    public SlideHeaderViewHolder(List<String> picture, ViewGroup parent) {
        super(picture, parent);
    }

    @Override
    public View onCreateView() {
        return LayoutInflater.from(getParent().getContext())
                             .inflate(R.layout.item_header_home_list, getParent(), false);
    }

    @Override
    public void initView() {
        mViewPager.setAdapter(new AdvertImagePagerAdapter(x.app(), getData()));
        indicator.setViewPager(mViewPager);
        mViewPager.setInterval(3000);
        mViewPager.startAutoScroll();
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % getData().size());
    }


    @Override
    public void onUpdateView() {

    }
}
