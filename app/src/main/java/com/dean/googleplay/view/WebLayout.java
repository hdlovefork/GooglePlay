package com.dean.googleplay.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.dean.googleplay.R;

import org.xutils.common.task.AbsTask;
import org.xutils.x;

import java.util.List;

/**
 * 允许后台执行耗时请求的布局，在fetchDataFromServer()方法中后台执行耗时的操作，
 * createPagerSuccess()方法执行在主线程并返回一个内容页面
 */
public abstract class WebLayout<ServerResultType> extends FrameLayout {

    private View mPagerLoading;
    private View mPagerError;
    private View mPagerEmpty;
    private View mPagerSuccess;
    protected PageStatus mStatus;

    public WebLayout(Context context) {
        super(context);
        init();
    }

    protected void init() {
        mStatus = PageStatus.UNLOAD;
        mPagerLoading = createPagerLoading();
        if (mPagerLoading != null) {
            addView(mPagerLoading);
        }
        mPagerError = createPagerError();
        if (mPagerError != null) {
            addView(mPagerError);
        }
        mPagerEmpty = createPagerEmpty();
        if (mPagerEmpty != null) {
            addView(mPagerEmpty);
        }
    }

    protected View createPagerEmpty() {
        return View.inflate(x.app(), R.layout.loading_page_empty, null);
    }

    protected View createPagerError() {
        return View.inflate(x.app(), R.layout.loading_page_error, null);
    }

    protected View createPagerLoading() {
        return View.inflate(x.app(), R.layout.loading_page_loading, null);
    }

    public abstract View createPagerSuccess(ServerResultType serverResult);

    public abstract ServerResultType fetchDataFromServer();

    public void show() {
        if (mStatus == PageStatus.EMPTY || mStatus == PageStatus.ERROR) {
            mStatus = PageStatus.LOADING;
        }
        if (mStatus == PageStatus.UNLOAD) {
            mStatus = PageStatus.LOADING;
            asyncFetchData();
        }
        updateView();
    }

    //根据状态刷新UI界面
    protected void updateView() {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                if (mPagerLoading != null) {
                    mPagerLoading
                            .setVisibility(mStatus == PageStatus.LOADING ? VISIBLE : INVISIBLE);
                }
                if (mPagerError != null) {
                    mPagerError.setVisibility(mStatus == PageStatus.ERROR ? VISIBLE : INVISIBLE);
                }
                if (mPagerEmpty != null) {
                    mPagerEmpty.setVisibility(mStatus == PageStatus.EMPTY ? VISIBLE : INVISIBLE);
                }
                if (mPagerSuccess != null) {
                    mPagerSuccess
                            .setVisibility(mStatus == PageStatus.SUCCESS ? VISIBLE : INVISIBLE);
                }
            }
        });
    }

    //开启一个线程从远程服务器获取数据
    private void asyncFetchData() {
        x.task().start(new AbsTask<ServerResultType>() {
            @Override
            protected ServerResultType doBackground() {
                return fetchDataFromServer();
            }

            @Override
            protected void onSuccess(ServerResultType result) {
                if (result == null) {
                    mStatus = PageStatus.ERROR;
                    updateView();
                    return;
                }
                if (result instanceof List) {
                    List list = (List) result;
                    if (list.size() == 0) {
                        mStatus = PageStatus.EMPTY;
                        updateView();
                        return;
                    }
                }
                mPagerSuccess = createPagerSuccess(result);
                mStatus = mPagerSuccess == null ? PageStatus.ERROR : PageStatus.SUCCESS;
                if (mPagerSuccess != null) {
                    addView(mPagerSuccess);
                }
                updateView();
            }

            @Override
            protected void onError(Throwable ex, boolean isCallbackError) {
                mStatus = PageStatus.ERROR;
                updateView();
            }
        });
    }

    public enum PageStatus {
        UNLOAD, LOADING, EMPTY, ERROR, SUCCESS
    }
}
