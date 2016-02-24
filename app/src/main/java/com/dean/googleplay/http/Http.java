package com.dean.googleplay.http;

import org.xutils.x;

/**
 * Created by Administrator on 2016/1/17.
 */
public class Http {

    public static <T> T getSync(BaseRequestParams params, Class<T> resultCls) {
        T data = null;
        try {
            Thread.sleep(1000);
            data = x.http().getSync(params, resultCls);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return data;
    }

}
