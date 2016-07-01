package com.makedream.util;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by dingdj on 2016/6/30.
 */

public class HttpUtil {

    /**
     * 加载数据
     * @param url
     * @return
     */
    public static String loadUrl(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();
        Request.Builder r_builder = new Request.Builder();
        Request request = r_builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response != null && response.isSuccessful()) {
                return  response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
