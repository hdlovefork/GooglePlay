package com.dean.googleplay.http;

import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Administrator on 2016/1/18.
 */
@HttpRequest(path = "recommend",builder = BaseRequestParams.MyParamsBuilder.class)
public class RecommendRequestParams extends BaseRequestParams {
}