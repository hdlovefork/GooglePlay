package com.dean.googleplay.domain;

import com.dean.googleplay.http.GsonParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17.
 */
@HttpResponse(parser = GsonParser.class)
public class HomeData {
    public List<String> picture;
    public AppList list;
}
