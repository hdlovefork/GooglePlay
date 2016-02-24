package com.dean.googleplay.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持页头和页脚的RecycleView
 */
public class WrapRecyclerView extends RecyclerView {

    private List<ViewHolder> mHeaderViews = new ArrayList<>();

    private List<ViewHolder> mFootViews = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(ViewHolder viewHolder) {
        mHeaderViews.add(viewHolder);
        if (mAdapter != null) {
            if (!(mAdapter instanceof Adapter)) {
                mAdapter = new Adapter(mHeaderViews, mFootViews, mAdapter);
                //                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addFooterView(ViewHolder viewHolder) {
        mFootViews.add(viewHolder);
        if (mAdapter != null) {
            if (!(mAdapter instanceof Adapter)) {
                mAdapter = new Adapter(mHeaderViews, mFootViews, mAdapter);
                //                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mHeaderViews.isEmpty() && mFootViews.isEmpty() || adapter == null) {
            super.setAdapter(adapter);
        } else {
            adapter = new Adapter(mHeaderViews, mFootViews, adapter);
            super.setAdapter(adapter);
        }
        mAdapter = adapter;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public class Adapter extends RecyclerView.Adapter {

        private RecyclerView.Adapter mAdapter;

        private final int MAX_HEADER_SIZE = Integer.MAX_VALUE / 2;
        //private final int MAX_FOOTER_SIZE = 100;

        //    protected List<ItemDataType> mList;

        public List<ViewHolder> getHeaderViews() {
            return mHeaderViews;
        }

        public List<ViewHolder> getFooterViews() {
            return mFooterViews;
        }

        private List<ViewHolder> mHeaderViews;

        private List<ViewHolder> mFooterViews;

        //    private int mCurrentPosition;

        public Adapter(List<ViewHolder> headerList, List<ViewHolder> footerList, RecyclerView.Adapter adapter) {
            this.mAdapter = adapter;
            //        mList = mData;
            this.mHeaderViews = headerList;
            this.mFooterViews = footerList;
            if (mHeaderViews == null) {
                this.mHeaderViews = new ArrayList<>();
            }
            if (mFooterViews == null) {
                this.mFooterViews = new ArrayList<>();
            }
        }


        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFooterViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //viewType为负数表示页头或页脚
            //-1 ~ -MAX_HEADER_SIZE:表示页头，默认为-1 ~ -100
            //-MAX_HEADER_SIZE-1 ~ - 0XFFFFFFFF :表示页脚，默认：-101 ~ -0xffffffff
            //如果是页头或页脚直接从mHeaderViews或mFooterViews里面取得ViewHolder
            if (viewType < 0) {//页头或页脚
                int index = ~viewType;
                if (index < MAX_HEADER_SIZE) { //判断是不是页头
                    //页头
                    if (index < mHeaderViews.size()) {
                        ViewHolder viewHolder = mHeaderViews.get(index);
                        return viewHolder == null ? null : viewHolder.getViewHolder();
                    } else {
                        return null;
                    }
                } else {//否则为页脚
                    //页脚
                    index -= MAX_HEADER_SIZE;//计算页脚的下标
                    if (index < mFooterViews.size()) {
                        ViewHolder viewHolder = mFooterViews.get(index);
                        return viewHolder == null ? null : viewHolder.getViewHolder();
                    } else {
                        return null;
                    }
                }
            }
            //不是页头或页脚的情况直接调用原适配器
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return;//页头不更新视图
            }
            int adjPosition = position - numHeaders;
            if (mAdapter != null) {
                int adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mAdapter.onBindViewHolder(holder, adjPosition);
                } else {
                    //页脚不更新视图
                }
            }
        }


        @Override
        public int getItemCount() {
            if (mAdapter != null) {
                return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            //返回值为负数表示页头或者页脚
            //-1 >=  页头的范围 > ~MAX_HEADER_SIZE
            //~MAX_HEADER_SIZE >= 页脚的范围 >= ~Integer.MAX_VALUE
            //position为0对应类型为-1
            //position为1对应类型为-2
            //...
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return ~position;//返回页头下标
            }
            //position在内容页或页脚中
            int adjPosition = position - numHeaders;
            if (mAdapter != null) {
                int adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {//在内容页中返回内容页
                    return mAdapter.getItemViewType(position);
                } else {//在页脚中
                    return ~(position - numHeaders - adapterCount + MAX_HEADER_SIZE);//返回页脚
                }
            }
            //这里不会执行
            return -1;
        }


        @Override
        public long getItemId(int position) {
            int numHeaders = getHeadersCount();
            if (mAdapter != null && position >= numHeaders) {
                int adjPosition = position - numHeaders;
                int adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }


    }

    public static abstract class ViewHolder<BindDataType> implements ViewDataBind<BindDataType> {

        private RecyclerView.ViewHolder mViewHolder;
        private ViewGroup mParent;
        private BindDataType mData;

        public ViewHolder() {
            this(null, null);
        }

        public ViewHolder(ViewGroup parent) {
            this(null, parent);
        }

        public ViewHolder(BindDataType data, ViewGroup parent) {
            this.mData = data;
            mParent = parent;
            View itemView = onCreateView();
            x.view().inject(this, itemView);
            mViewHolder = new RecyclerView.ViewHolder(itemView) {
            };
            mViewHolder.itemView.setTag(this);
            initView();
        }

        /**
         * 获取ViewHolder所在的父视图
         *
         * @return
         */
        public ViewGroup getParent() {
            return mParent;
        }

        public RecyclerView.ViewHolder getViewHolder() {
            return mViewHolder;
        }

        public BindDataType getData() {
            return mData;
        }

        public void setData(BindDataType data) {
            mData = data;
        }

        public View getView() {
            return mViewHolder.itemView;
        }
    }

}
