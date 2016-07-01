package com.makedream.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.makedream.jawasi.config.Constant;
import com.makedream.jawasi.model.IndexItem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/6/30.
 */
public class HttpUtilTest {
    @Test
    public void loadUrl() throws Exception {
        String response = HttpUtil.loadUrl(Constant.GOLD_URL);
        Type listType = new TypeToken<List<IndexItem>>(){}.getType();
        JSONArray jsonArray = new JSONArray(response);
        Gson gson = new Gson();
        List<IndexItem> indexItems = gson.fromJson(response, listType);
        for (IndexItem indexItem : indexItems) {
            System.out.println(indexItem);
        }
    }

}