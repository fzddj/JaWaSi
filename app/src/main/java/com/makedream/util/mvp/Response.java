package com.makedream.util.mvp;

import java.util.Collection;

/**
 * Created by dingdj on 2016/6/2.
 */
public class Response<T> {

    public static final int RESPONSE_SUCCESS = 0;

    public static final int RESPONSE_NETWORK_ERROR = -1;

    public static final int RESPONSE_LOADING = -2;



    public int code;

    public Collection<T> datas;

    public String msg;

    public Response(int code) {
        this.code = code;
    }
}
