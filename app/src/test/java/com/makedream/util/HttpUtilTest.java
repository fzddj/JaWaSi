package com.makedream.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.makedream.jawasi.config.Constant;
import com.makedream.jawasi.model.IndexItem;
import com.makedream.jawasi.model.Stock;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/6/30.
 */
public class HttpUtilTest {
    @Test
    public void loadUrl() throws Exception {
//        String response = HttpUtil.loadUrl(Constant.GOLD_URL);
//        Type listType = new TypeToken<List<IndexItem>>(){}.getType();
//        JSONArray jsonArray = new JSONArray(response);
//        Gson gson = new Gson();
//        List<IndexItem> indexItems = gson.fromJson(response, listType);
//        for (IndexItem indexItem : indexItems) {
//            System.out.println(indexItem);
//        }

        String[] stockIds = new String[]{"sz000851","sh600366","sh600983"};
        loadStocks(stockIds);
    }



    public List<Stock> loadStocks(String[] stockIds) {
        List<Stock> list = new ArrayList<>();
        String url = formatUrl(stockIds);
        System.out.println(url);
        String response = HttpUtil.loadUrl(url);
        String[] stockInfos = response.split(";");
        for (String stockInfo : stockInfos) {
            Stock stock = new Stock();
            int index = stockInfo.indexOf("=");
            if(index == -1) continue;
            String stockId = stockInfo.substring(index-6, index);
            stock.setId(Long.parseLong(stockId));
            String stockContent = stockInfo.substring(index+2, stockInfo.length()-1);
            String[] stockItems = stockContent.split(",");
            stock.setStockName(stockItems[0]);
            stock.setCurrentPrice(Double.parseDouble(stockItems[3]));
        }
        return list;

    }


    private String formatUrl(String[] stockIds) {
        if(stockIds == null) return Constant.STOCK_URL;
        String url = Constant.STOCK_URL;
        for (String stockId : stockIds) {
            url = url +stockId + ",";
        }
        return url;
    }

}