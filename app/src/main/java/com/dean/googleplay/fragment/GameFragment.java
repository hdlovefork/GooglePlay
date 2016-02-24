package com.dean.googleplay.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.adapter.AbsAdapter;
import com.dean.googleplay.adapter.AppListAdapter;
import com.dean.googleplay.domain.AppList;
import com.dean.googleplay.http.GameRequestParams;
import com.dean.googleplay.http.Http;
import com.dean.googleplay.recyclerview.WrapRecyclerView;
import com.dean.googleplay.viewholder.AppViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/1/10.
 */
public class GameFragment extends AppListFragment<AppList> {

    private final GameRequestParams mParams;
    private AppList mAppList;

    public GameFragment() {
        mParams = new GameRequestParams();
    }

    @Override
    protected AppList fetchDataFromServer() {
        return Http.getSync(mParams, AppList.class);
    }

    @Override
    protected View createPagerSuccess(AppList serverResult) {
        mAppList = serverResult;
        mWrapRecyclerView = new WrapRecyclerView(x.app());
        mWrapRecyclerView.setItemAnimator(null);
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(x.app()));
        GameAdapter gameAdapter = new GameAdapter(mAppList);
        mWrapRecyclerView.setAdapter(gameAdapter);
        mWrapRecyclerView.addOnScrollListener(new AbsAdapter.OnRecyclerLoadMoreListener() {
            @Override
            public int getPageSize() {
                return 21;
            }

            @Override
            public int onLoadMore() {
                AppList list = Http.getSync(mParams.setIndex(mAppList.size()), AppList.class);
                if (list != null) {
                    mAppList.addAll(list);
                    return list.size();
                }
                return 0;
            }
        });
        return mWrapRecyclerView;
    }

    private class GameAdapter extends AppListAdapter<AppList.AppInfo> {

        public GameAdapter(List list) {
            super(list);
        }

        @Override
        public WrapRecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
            return new AppViewHolder(parent);
        }
    }
}
