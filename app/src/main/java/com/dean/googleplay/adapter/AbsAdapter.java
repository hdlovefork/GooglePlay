package com.dean.googleplay.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dean.googleplay.recyclerview.ViewDataBind;
import com.dean.googleplay.recyclerview.WrapRecyclerView;
import com.dean.googleplay.viewholder.DefaultFooterViewHolder;

import org.xutils.common.task.AbsTask;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.List;

/**
 * 增加滚动底部视图刷新事件，增加item点击事件
 */
public abstract class AbsAdapter<ItemDataType> extends RecyclerView.Adapter {

    protected List<ItemDataType> mList;

    public AbsAdapter(List<ItemDataType> list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WrapRecyclerView.ViewHolder viewHolder = onCreateItemViewHolder(parent);
        if (viewHolder != null && viewHolder.getView() != null) {
            viewHolder.getView()
                      .setOnClickListener(new MyOnItemClickListener(viewHolder, parent));
        }
        return viewHolder == null ? null : viewHolder.getViewHolder();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mList != null && holder != null && position >= 0 && position < mList
                .size()) {
            WrapRecyclerView.ViewHolder viewHolder = (WrapRecyclerView.ViewHolder) holder.itemView
                    .getTag();
            if (viewHolder != null) {
                viewHolder.setData(mList.get(position));
                viewHolder.onUpdateView();
            }
        }
    }

    protected abstract WrapRecyclerView.ViewHolder<ItemDataType> onCreateItemViewHolder(ViewGroup parent);


    public static abstract class OnRecyclerLoadMoreListener extends WrapRecyclerView.OnScrollListener {

        private LinearLayoutManager mLayoutManager;
        private int mItemCount, mLastCompletely, mLastLoad;

        /**
         * 每页的记录条数
         *
         * @return
         */
        public abstract int getPageSize();

        /**
         * 子线程中加载更多数据
         *
         * @return 返回成功添加到数据集的条数<br/>-1：网络出错;<br/> >=0 && < getPageSize()没有更多;<br/>==getPageSize() 还有更多数据待加载
         */
        public abstract int onLoadMore();

        @Override
        public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                mItemCount = mLayoutManager.getItemCount();
                mLastCompletely = mLayoutManager.findLastCompletelyVisibleItemPosition();
            } else {
                return;
            }

            /**
             * mItemCount==mLastCompletely + 1拉到底时加载
             * mLastLoad!=mItemCount每次拉到底时只加载一次
             */
            if (mLastLoad != mItemCount && mItemCount == mLastCompletely + 1) {
                LogUtil.d(String.format("****************load more****************"));
                LogUtil.d(String
                        .format("mLastLoad:%d \t mItemCount:%d \t mLastCompletely:%d", mLastLoad, mItemCount, mLastCompletely));
                LogUtil.d(String.format("****************load more****************"));
                mLastLoad = mItemCount;
                x.task().start(new AbsTask<Integer>() {

                    @Override
                    protected Integer doBackground() throws Throwable {
                        int count = onLoadMore();
                        return count;
                    }

                    @Override
                    protected void onSuccess(Integer count) {
                        int footerState;
                        if (count >= 0 && count < getPageSize()) {
                            footerState = DefaultFooterViewHolder.NO_MORE;
                        } else if (count == getPageSize()) {
                            footerState = DefaultFooterViewHolder.HAS_MORE;
                        } else {
                            footerState = DefaultFooterViewHolder.ERROR;
                        }
                        if (recyclerView.getAdapter() instanceof WrapRecyclerView.Adapter) {
                            WrapRecyclerView.Adapter adapter = (WrapRecyclerView.Adapter) recyclerView
                                    .getAdapter();
                            ViewDataBind viewHolder = adapter.getFooterViews()
                                                             .get(0);
                            if (viewHolder != null) {
                                viewHolder.setData(footerState);
                                viewHolder.onUpdateView();
                            }
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    protected void onError(Throwable ex, boolean isCallbackError) {
                        if (recyclerView.getAdapter() instanceof WrapRecyclerView.Adapter) {
                            WrapRecyclerView.Adapter adapter = (WrapRecyclerView.Adapter) recyclerView
                                    .getAdapter();
                            ViewDataBind viewHolder = adapter.getFooterViews()
                                                             .get(0);
                            if (viewHolder != null) {
                                viewHolder.setData(DefaultFooterViewHolder.ERROR);
                                viewHolder.onUpdateView();
                            }
                        }
                    }
                });
            }
        }
    }


    //footer_view 的 ViewHolder


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(WrapRecyclerView.ViewHolder viewHolder, ViewGroup parent, View v);
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    private class MyOnItemClickListener implements View.OnClickListener {
        private final WrapRecyclerView.ViewHolder mHolder;
        private final ViewGroup mParent;

        public MyOnItemClickListener(WrapRecyclerView.ViewHolder holder, ViewGroup parent) {
            mHolder = holder;
            mParent = parent;
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClickListener != null) {
                mOnRecyclerViewItemClickListener.onItemClick(mHolder, mParent, v);
            }
        }
    }
}
