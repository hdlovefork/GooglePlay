package com.dean.googleplay.http;

import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Administrator on 2016/1/26.
 */
@HttpRequest(path = "category",builder = BaseRequestParams.MyParamsBuilder.class)
public class CategoryRequestParams extends BaseRequestParams {
}
