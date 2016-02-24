package com.dean.googleplay.http;

import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Administrator on 2016/1/18.
 */
@HttpRequest(path = "game",builder = BaseRequestParams.MyParamsBuilder.class)
public class GameRequestParams extends BaseRequestParams {
}