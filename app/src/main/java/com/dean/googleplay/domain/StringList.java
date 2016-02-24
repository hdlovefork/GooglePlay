package com.dean.googleplay.domain;

import com.dean.googleplay.http.GsonParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/23.
 */
@HttpResponse(parser = GsonParser.class)
public class StringList extends ArrayList<String> {

}
