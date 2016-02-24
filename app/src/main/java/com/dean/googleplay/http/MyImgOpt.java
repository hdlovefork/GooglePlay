package com.dean.googleplay.http;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/1/17.
 */
public class MyImgOpt {

    private static org.xutils.image.ImageOptions mImageOptions;

    public static org.xutils.image.ImageOptions build(int defaultDrawableId) {
        if (mImageOptions == null) {
            org.xutils.image.ImageOptions.Builder builder = new org.xutils.image.ImageOptions.Builder()
                    .setSize(0,0)
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setLoadingDrawableId(defaultDrawableId)
                    .setFailureDrawableId(defaultDrawableId);
            mImageOptions = builder.build();
        }
        return mImageOptions;
    }
}
