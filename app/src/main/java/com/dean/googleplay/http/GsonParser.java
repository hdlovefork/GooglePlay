package com.dean.googleplay.http;

import com.google.gson.Gson;

import org.xutils.common.util.LogUtil;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/1/17.
 */
public class GsonParser implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        Gson gson = new Gson();
        LogUtil.d("服务器结果："+result);
        return gson.fromJson(result, resultType);
    }
}
