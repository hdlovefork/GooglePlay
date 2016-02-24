package com.dean.googleplay.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.adapter.AbsAdapter;
import com.dean.googleplay.adapter.AppListAdapter;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.domain.HomeData;
import com.dean.googleplay.http.HomeRequestParams;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.recyclerview.WrapRecyclerView;
import com.dean.googleplay.viewholder.AppViewHolder;
import com.dean.googleplay.viewholder.DefaultFooterViewHolder;
import com.dean.googleplay.viewholder.SlideHeaderViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/1/10.
 */

public class HomeFragment extends AppListFragment<HomeData> {

    private static final String TAG = "HomeFragment";
    private HomeData mHomeData;
    private HomeRequestParams mHomeRequestParams;

    public HomeFragment() {
        mHomeRequestParams = new HomeRequestParams();
    }

    @Override
    protected HomeData fetchDataFromServer() {
        return Http.getSync(mHomeRequestParams, HomeData.class);
    }

    @Override
    protected View createPagerSuccess(HomeData serverResult) {
        mHomeData = serverResult;
        mWrapRecyclerView = new WrapRecyclerView(x.app());
        mWrapRecyclerView.setItemAnimator(null);
        HomeAdapter homeAdapter = new HomeAdapter(mHomeData.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(x.app());
        mWrapRecyclerView.setLayoutManager(linearLayoutManager);
        mWrapRecyclerView
                .addHeaderView(new SlideHeaderViewHolder(mHomeData.picture, mWrapRecyclerView));
        mWrapRecyclerView.addFooterView(new DefaultFooterViewHolder(mWrapRecyclerView));
        mWrapRecyclerView.setAdapter(homeAdapter);
        mWrapRecyclerView.addOnScrollListener(new AbsAdapter.OnRecyclerLoadMoreListener() {
            @Override
            public int getPageSize() {
                return 20;
            }

            @Override
            public int onLoadMore() {
                HomeData data = Http
                        .getSync(mHomeRequestParams
                                .setIndex(mHomeData.list.size()), HomeData.class);
                if (data != null && data.list != null) {
                    mHomeData.list.addAll(data.list);
                    return data.list.size();
                }
                return 0;
            }
        });
        return mWrapRecyclerView;
    }

    class HomeAdapter extends AppListAdapter<AppList.AppInfo> {

        public HomeAdapter(List list) {
            super(list);
        }

        @Override
        public WrapRecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
            return new AppViewHolder(parent);
        }

    }
}
