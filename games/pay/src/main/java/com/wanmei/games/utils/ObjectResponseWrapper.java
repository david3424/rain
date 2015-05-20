package com.wanmei.games.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-10-28
 * Time: 上午11:06
 * spring mvc集成jackson 返回一个@ResponseBody 的object 会直接被jackson 转化成json
 * 该类提供一些包装方法，让返回的对象适应前台功能js可以接受的Object格式，也就是json的格式
 */
public class ObjectResponseWrapper {


    public static Map<String, Object> commonResponse(Boolean success, Integer status, String message) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("success", success);
        result.put("status", status);
        result.put("message", message);
        return result;
    }

    public static Map<String, Object> commonResponse(Boolean success, String message) {
        return commonResponse(success, 0, message);
    }
}
