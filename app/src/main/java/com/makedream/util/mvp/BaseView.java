package com.makedream.util.mvp;

import java.util.Collection;

/**
 * Created by dingdj on 2016/6/2.
 */
public interface BaseView<T> {

    void showLoading();

    void showData(Collection<T> datas);

    void showEmptyView();

    void showLoadMore();

    void showMessage(String msg);

    void showLoadNew();

    void showNetErrorView(int code);

}
