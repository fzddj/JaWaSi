package com.makedream.util.mvp;



import com.makedream.jawasi.Global;
import com.makedream.util.TelephoneUtil;
import com.makedream.util.ThreadUtil;

import java.util.Map;

/**
 * Created by dingdj on 2016/6/2.
 */
public class BasePresenter {

    DefaultView mView;
    BaseRepo mRepo;

    public BasePresenter(BaseView baseView, BaseRepo repo) {
        if(baseView == null || repo == null) {
            throw new IllegalArgumentException("constuct error by null value");
        }
        mView = new DefaultView(baseView);
        mRepo = repo;
    }


    /**
     * 同步加载数据
     * @param paramsMap
     */
    public void loadDataSync(Map<String, Object> paramsMap) {
        if(!TelephoneUtil.isNetworkAvailable(Global.getContext())) {
            mView.showNetErrorView(0);
        }
        mView.showLoading();
        Response response = mRepo.loadData(paramsMap);
        if(response == null) mView.showMessage("未知错误");
        if(response.code == Response.RESPONSE_SUCCESS) {
            if(response.datas == null || response.datas.size() == 0) {
                mView.showEmptyView();
            } else {
                mView.showData(response.datas);
            }
        } else if(response.code == Response.RESPONSE_LOADING) {
            mView.showMessage("正在请求中...");
        } else{
            mView.showNetErrorView(response.code);
        }
    }

    /**
     * 加载更多数据
     * @param paramsMap
     */
    public void loadDataMoreSync(Map<String, Object> paramsMap) {
        if(!TelephoneUtil.isNetworkAvailable(Global.getContext())) {
            mView.showNetErrorView(0);
        }
        mView.showLoadMore();
        Response response = mRepo.loadMoreData(paramsMap);
        if(response == null) mView.showMessage("未知错误");
        if(response.code == Response.RESPONSE_SUCCESS) {
            if(response.datas == null || response.datas.size() == 0) {
                mView.showMessage("已经到达列表底部");
            } else {
                mView.showData(response.datas);
            }
        } else if(response.code == Response.RESPONSE_LOADING) {
            mView.showMessage("正在请求中...");
        } else{
            mView.showNetErrorView(response.code);
        }
    }


    /**
     * 加载最新数据
     * @param paramsMap
     */
    public void loadDataNewSync(Map<String, Object> paramsMap) {
        if(!TelephoneUtil.isNetworkAvailable(Global.getContext())) {
            mView.showNetErrorView(0);
        }
        mView.showLoadNew();
        Response response = mRepo.loadNewData(paramsMap);
        if(response == null) mView.showMessage("未知错误");
        if(response.code == Response.RESPONSE_SUCCESS) {
            if(response.datas == null || response.datas.size() == 0) {
                mView.showMessage("已是最新的数据了");
            } else {
                mView.showData(response.datas);
            }
        } else if(response.code == Response.RESPONSE_LOADING) {
            mView.showMessage("正在请求中...");
        } else{
            mView.showNetErrorView(response.code);
        }
    }



    /**
     * 同步加载数据
     * @param paramsMap
     */
    public void loadDataAsync(final Map<String, Object> paramsMap) {
        if(!TelephoneUtil.isNetworkAvailable(Global.getContext())) {
            mView.showNetErrorView(0);
        }
        mView.showLoading();
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                final Response response = mRepo.loadData(paramsMap);
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response == null) mView.showMessage("未知错误");
                        if(response.code == Response.RESPONSE_SUCCESS) {
                            if(response.datas == null || response.datas.size() == 0) {
                                mView.showEmptyView();
                            } else {
                                mView.showData(response.datas);
                            }
                        } else if(response.code == Response.RESPONSE_LOADING) {
                            mView.showMessage("正在请求中...");
                        } else{
                            mView.showNetErrorView(response.code);
                        }
                    }
                });
            }
        });

    }

    /**
     * 加载更多数据
     * @param paramsMap
     */
    public void loadDataMoreAsync(final Map<String, Object> paramsMap) {
        if(!TelephoneUtil.isNetworkAvailable(Global.getContext())) {
            mView.showNetErrorView(0);
        }
        mView.showLoadMore();
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                final Response response = mRepo.loadMoreData(paramsMap);
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response == null) mView.showMessage("未知错误");
                        if(response.code == Response.RESPONSE_SUCCESS) {
                            if(response.datas == null || response.datas.size() == 0) {
                                mView.showMessage("已经到达列表底部");
                            } else {
                                mView.showData(response.datas);
                            }
                        } else if(response.code == Response.RESPONSE_LOADING) {
                            mView.showMessage("正在请求中...");
                        } else{
                            mView.showNetErrorView(response.code);
                        }
                    }
                });
            }
        });

    }


    /**
     * 加载最新数据
     * @param paramsMap
     */
    public void loadDataNewAsync(final Map<String, Object> paramsMap) {
        if(!TelephoneUtil.isNetworkAvailable(Global.getContext())) {
            mView.showNetErrorView(0);
        }
        mView.showLoadNew();
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                final Response response = mRepo.loadNewData(paramsMap);
                Global.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response == null) mView.showMessage("未知错误");
                        if(response.code == Response.RESPONSE_SUCCESS) {
                            if(response.datas == null || response.datas.size() == 0) {
                                mView.showMessage("已是最新的数据了");
                            } else {
                                mView.showData(response.datas);
                            }
                        } else if(response.code == Response.RESPONSE_LOADING) {
                            mView.showMessage("正在请求中...");
                        } else{
                            mView.showNetErrorView(response.code);
                        }
                    }
                });

            }
        });
    }



}
