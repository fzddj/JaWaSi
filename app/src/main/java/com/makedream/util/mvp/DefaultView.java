package com.makedream.util.mvp;

import java.lang.ref.WeakReference;
import java.util.Collection;

/**
 * Created by dingdj on 2016/6/2.
 */

public class DefaultView implements BaseView {

    private WeakReference<BaseView> mViewWeakReference;

    public DefaultView(BaseView view) {
        mViewWeakReference = new WeakReference<BaseView>(view);
    }

    @Override
    public void showLoading() {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showLoading();
        }
    }

    @Override
    public void showData(Collection datas) {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showData(datas);
        }
    }

    @Override
    public void showEmptyView() {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showEmptyView();
        }
    }

    @Override
    public void showLoadMore() {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showLoadMore();
        }
    }

    @Override
    public void showMessage(String msg) {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showMessage(msg);
        }
    }

    @Override
    public void showLoadNew() {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showLoadNew();
        }
    }

    @Override
    public void showNetErrorView(int code) {
        BaseView view = mViewWeakReference.get();
        if(view != null) {
            view.showNetErrorView(code);
        }
    }
}
