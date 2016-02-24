package com.dean.googleplay.http;

import android.os.Environment;

import org.xutils.common.util.FileUtil;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.x;

import java.io.File;

/**
 * Created by Administrator on 2016/1/18.
 */
@HttpRequest(path = "download", builder = BaseRequestParams.MyParamsBuilder.class)
public class DownloadRequestParams extends org.xutils.http.RequestParams {
    private String name;

    public DownloadRequestParams(String downloadUrl) {
        name = downloadUrl;
        setCancelFast(true);
        setAutoResume(true);
        setAutoRename(false);
        String suffixPath =  File.separator + "download" +File.separator + name;
        String rootDir = null;
        if (FileUtil.existsSdcard() || !Environment.isExternalStorageRemovable()) {
            rootDir = x.app().getExternalCacheDir().getAbsolutePath();
        } else {
            rootDir=x.app().getCacheDir().getAbsolutePath();
        }
        setSaveFilePath(rootDir + suffixPath);
    }
}