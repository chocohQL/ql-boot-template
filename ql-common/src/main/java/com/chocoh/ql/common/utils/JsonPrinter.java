package com.chocoh.ql.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chocoh
 */
public class JsonPrinter {
    public static void printJson(Object obj) {
        JSON json = JSON.parseObject(JSON.toJSONString(obj));
        String formattedJsonString = JSONObject.toJSONString(json, true);
        System.out.println(formattedJsonString);
    }
}