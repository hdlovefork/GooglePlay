package com.dean.googleplay.http;

import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Administrator on 2016/1/18.
 */
@HttpRequest(path = "detail",builder = BaseRequestParams.MyParamsBuilder.class)
public class AppDetailRequestParams extends BaseRequestParams {

    private String packageName;

    public AppDetailRequestParams(String packageName){
        this.packageName=packageName;
    }
}