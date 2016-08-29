package com.makedream.util;

/**
 * Created by ddj on 16-8-6.
 */
public class StockUtil {

    public static String getStockExByStockId(String stockId) {

        if(stockId.startsWith("6")) {
            return "sh";
        }
        return "sz";
    }
}
