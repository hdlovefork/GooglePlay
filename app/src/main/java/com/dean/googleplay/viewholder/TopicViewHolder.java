package com.dean.googleplay.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.googleplay.R;
import com.dean.googleplay.domain.TopicList;
import com.dean.googleplay.http.MyImgOpt;
import com.dean.googleplay.recyclerview.WrapRecyclerView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/1/23.
 */
public class TopicViewHolder extends WrapRecyclerView.ViewHolder<TopicList.TopicInfo> {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.iv_pic)
    private ImageView iv_pic;

    public TopicViewHolder(ViewGroup parent) {
        super(parent);
    }


    @Override
    public View onCreateView() {
        return LayoutInflater.from(getParent().getContext())
                             .inflate(R.layout.item_topic_list, getParent(), false);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onUpdateView() {
        TopicList.TopicInfo data = getData();
        if(data==null) return;
        tv_title.setText(data.des);
        x.image().bind(iv_pic, x.app().getString(R.string.server_addr) + "/image?name=" + data
                .url, MyImgOpt.build(R.drawable.ic_default));
    }
}
