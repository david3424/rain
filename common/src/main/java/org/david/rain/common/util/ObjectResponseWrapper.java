package org.david.rain.common.util;


import org.david.rain.common.lang.CommonList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 13-10-28
 * Time: 上午11:06
 * spring mvc集成jackson 返回一个@ResponseBody 的object 会直接被jackson 转化成json
 * 该类提供一些包装方法，让返回的对象适应前台功能js可以接受的Object格式，也就是json的格式
 */
public class ObjectResponseWrapper {

    /**
     * 本方法将CommonList 对象转化成一个 活动pagination 插件可以使用的Map 返回对象
     *
     * @param list
     * @return
     */
    public static Map<String, Object> createPagenationMap(CommonList list) {
        return createPagenationMap(list, true, 0, "");
    }

    public static Map<String, Object> createPagenationMap(CommonList list, Boolean success, String message) {
        return createPagenationMap(list, success, 0, message);
    }

    public static Map<String, Object> createPagenationMap(CommonList list, Boolean success, Integer status, String message) {
        Map<String, Object> result = new HashMap<>();
        if (list != null) {
            result.put("totalCount", list.getRecNum());
            result.put("currentPage", list.pageNo);
            result.put("records", list);
        }
        result.put("success", success);
        result.put("status", status);
        result.put("message", message);
        return result;
    }

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
