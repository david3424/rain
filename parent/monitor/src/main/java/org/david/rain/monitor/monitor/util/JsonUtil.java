package org.david.rain.monitor.monitor.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by czw on 13-12-11.
 */
public class JsonUtil {

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
