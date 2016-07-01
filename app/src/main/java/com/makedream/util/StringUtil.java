package com.makedream.util;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

/**
 * 字符串工具类
 */
public final class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return boolean
     */
    public static boolean isEmpty(CharSequence s) {
        if ((s == null) || (s.length() <= 0) || s.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param strs
     * @return boolean
     */
    public static boolean isAnyEmpty(String... strs) {
        if (strs == null)
            return true;

        final int N = strs.length;
        for (int i = 0; i < N; i++) {
            if (isEmpty(strs[i]))
                return true;
        }

        return false;
    }

    /**
     * 判断传入的字符串是否是数字类型
     *
     * @param sNum
     * @return boolean
     */
    public static boolean isNumberic(String sNum) {
        try {
            Float.parseFloat(sNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 重命名文件，规则：.png => .a; .jpg => .b
     *
     * @param path
     * @return String
     */
    public static String renameRes(String path) {
        if (path == null) {
            return null;
        }
        return path.replace(".png", ".a").replace(".jpg", ".b");
    }

    /**
     * 解析int
     *
     * @param str
     * @param defaultValue 默认值
     * @return int
     */
    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String encodeUrlParams(String value) {
        String returnValue = "";

        try {
            value = URLEncoder.encode(value, "UTF-8");
            returnValue = value.replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return returnValue;
    }

    public static String getUserIdString(Collection<Long> list) {
        if (list == null || list.size() < 1) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Long uid : list) {
            sb.append(uid + "");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getJsonString(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null || "".equals(key.trim())) {
            return "";
        }

        String value = jsonObject.optString(key, "");
        if (value == null || "null".equals(value.trim())) {
            return "";
        }
        return value;
    }

    public static String getJsonString(JSONObject jsonObject, String key, String def) {
        if (jsonObject == null || key == null || "".equals(key.trim())) {
            return def;
        }

        String value = jsonObject.optString(key, def);
        if (value == null || "null".equals(value.trim())) {
            return def;
        }
        return value;
    }

    public static String getIfEmpty(String s){
        if(isEmpty(s)){
            return "";
        }
        return s;
    }

    public static String getIfEmpty(String s, String def){
        if(isEmpty(s)){
            return def;
        }
        return s;
    }

}
