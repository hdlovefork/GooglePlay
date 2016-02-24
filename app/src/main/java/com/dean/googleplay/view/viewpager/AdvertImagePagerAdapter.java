/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.dean.googleplay.view.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dean.googleplay.R;
import com.dean.googleplay.http.MyImgOpt;
import com.dean.googleplay.view.viewpager.salvage.RecyclingPagerAdapter;

import org.xutils.x;

import java.util.List;


/**
 * AdvertImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class AdvertImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<String> imageUrlList;

    private int size;

    public AdvertImagePagerAdapter(Context context, List<String> imageUrlList) {
        this.context = context;
        this.imageUrlList = imageUrlList;
        this.size = imageUrlList.size();
    }

    @Override
    public int getCount() {
        // Infinite loop
        return imageUrlList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return position % size;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        x.image().bind(holder.imageView, x.app()
                                          .getString(R.string.server_addr) + "/image?name=" + imageUrlList
                .get(getPosition(position)), MyImgOpt
                .build(R.drawable.ic_default));
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }


}
