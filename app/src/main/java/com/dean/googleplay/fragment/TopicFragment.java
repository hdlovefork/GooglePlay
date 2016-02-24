package com.dean.googleplay.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.adapter.AbsAdapter;
import com.dean.googleplay.viewholder.TopicViewHolder;
import com.dean.googleplay.domain.TopicList;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.http.TopicRequestParams;
import com.dean.googleplay.recyclerview.WrapRecyclerView;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/1/10.
 */
public class TopicFragment extends BaseFragment<TopicList> {

    private final TopicRequestParams mParams;
    private List mList;

    public TopicFragment() {
        mParams = new TopicRequestParams();
    }

    @Override
    protected TopicList fetchDataFromServer() {
        return Http.getSync(mParams, TopicList.class);
    }

    @Override
    protected View createPagerSuccess(TopicList serverResult) {
        mList = serverResult;
        RecyclerView recyclerView = new RecyclerView(x.app());
        recyclerView.setLayoutManager(new LinearLayoutManager(x.app()));
        recyclerView.setAdapter(new TopicAdapter(mList));
        recyclerView.addOnScrollListener(new AbsAdapter.OnRecyclerLoadMoreListener() {
            @Override
            public int getPageSize() {
                return 20;
            }

            @Override
            public int onLoadMore() {
                List list = Http.getSync(mParams.setIndex(mList.size()), TopicList.class);
                if (list != null) {
                    mList.addAll(list);
                    return list.size();
                }
                return 0;
            }
        });
        return recyclerView;
    }

    private class TopicAdapter extends AbsAdapter<TopicList.TopicInfo> {

        public TopicAdapter(List list) {
            super(list);
        }

        @Override
        public WrapRecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
            return new TopicViewHolder(parent);
        }
    }
}
