package com.dean.googleplay.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.adapter.AbsAdapter;
import com.dean.googleplay.adapter.AppListAdapter;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.http.AppRequestParams;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.recyclerview.WrapRecyclerView;
import com.dean.googleplay.viewholder.AppViewHolder;
import com.dean.googleplay.viewholder.DefaultFooterViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/1/10.
 */
public class AppFragment extends AppListFragment<AppList> {

    private static final String TAG = "AppFragment";
    private final AppRequestParams mAppRequestParams;
    private AppList mAppList;

    public AppFragment() {
        mAppRequestParams = new AppRequestParams();
    }

    @Override
    protected AppList fetchDataFromServer() {
        return Http.getSync(mAppRequestParams, AppList.class);
    }

    @Override
    protected View createPagerSuccess(AppList serverResult) {
        mAppList = serverResult;
        mWrapRecyclerView = new WrapRecyclerView(x.app());
        mWrapRecyclerView.setItemAnimator(null);
        final AppAdapter appAdapter = new AppAdapter(mAppList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(x.app());
        mWrapRecyclerView.setLayoutManager(linearLayoutManager);
        mWrapRecyclerView.addFooterView(new DefaultFooterViewHolder(mWrapRecyclerView));
        mWrapRecyclerView.setAdapter(appAdapter);

        mWrapRecyclerView.addOnScrollListener(new AbsAdapter.OnRecyclerLoadMoreListener() {
            @Override
            public int getPageSize() {
                return 20;
            }

            @Override
            public int onLoadMore() {
                AppList data = Http
                        .getSync(mAppRequestParams.setIndex(mAppList.size()), AppList.class);
                if (data != null && data != null) {
                    mAppList.addAll(data);
                    return data.size();
                }
                return 0;
            }
        });
        return mWrapRecyclerView;
    }

    private class AppAdapter extends AppListAdapter<AppList.AppInfo> {

        public AppAdapter(List<AppList.AppInfo> list) {
            super(list);
        }

        @Override
        public WrapRecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
            return new AppViewHolder(parent);
        }
    }
}
