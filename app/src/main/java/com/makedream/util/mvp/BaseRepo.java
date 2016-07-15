package com.makedream.util.mvp;

import java.util.Map;

/**
 * Created by dingdj on 2016/6/2.
 */
public abstract class BaseRepo<T> {

    protected int pageSize = 10;

    protected boolean isLastPage = false;

    protected boolean isLoading = false;


    protected abstract Response<T> _loadData(Map<String, Object> paramsMap);

    protected abstract Response<T> _loadDataMore(Map<String, Object> paramsMap);

    protected abstract Response<T> _loadDataNew(Map<String, Object> paramsMap);


    Response<T> loadData(Map<String, Object> paramsMap) {
        if(isLoading) {
            return createSimpleResonse(Response.RESPONSE_LOADING);
        }
        isLoading = true;
        Response response = null;
        try {
            response = _loadData(paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLoading = false;
        return response;
    }


    Response<T> loadMoreData(Map<String, Object> paramsMap) {
        if(isLoading) {
            return createSimpleResonse(Response.RESPONSE_LOADING);
        }
        if(isLastPage) {
            return createSimpleResonse(Response.RESPONSE_SUCCESS);
        }

        isLoading = true;
        Response response = null;
        try {
            response = _loadDataMore(paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLoading = false;
        return response;
    }


    Response<T> loadNewData(Map<String, Object> paramsMap) {
        if(isLoading) {
            return createSimpleResonse(Response.RESPONSE_LOADING);
        }
        isLoading = true;
        Response response = null;
        try {
            response = _loadDataNew(paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLoading = false;
        return response;
    }


    public boolean isLastPage() {
        return isLastPage;
    }


    public boolean isLoading() {
        return isLoading;
    }

    private static Response createSimpleResonse(int code) {
        return new Response(code);
    }

}
