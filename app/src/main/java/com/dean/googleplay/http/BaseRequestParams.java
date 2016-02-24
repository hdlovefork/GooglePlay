package com.dean.googleplay.http;

import com.dean.googleplay.R;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;
import org.xutils.x;

/**
 * Created by Administrator on 2016/1/17.
 */
public class BaseRequestParams extends org.xutils.http.RequestParams {

    public BaseRequestParams() {
    }

    public BaseRequestParams(int index) {
        this.index = index;
    }

    private int index;

    public int getIndex() {
        return index;
    }

    public BaseRequestParams setIndex(int index) {
        this.index = index;
        return this;
    }

    public static class MyParamsBuilder extends DefaultParamsBuilder {
        @Override
        public String buildUri(RequestParams params, HttpRequest httpRequest) {
            return x.app().getString(R.string.server_addr) + "/" + httpRequest.path();
        }
    }
}
