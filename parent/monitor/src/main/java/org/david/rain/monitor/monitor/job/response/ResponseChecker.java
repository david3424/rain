package org.david.rain.monitor.monitor.job.response;

import org.david.rain.monitor.lang.Tuple;
import org.david.rain.monitor.monitor.domain.RichServerItem;
import org.david.rain.monitor.monitor.job.response.common.CommonResponseChecker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by czw on 13-12-30.
 */
public abstract class ResponseChecker {
    public static Map<String, ResponseChecker> CHECKER_MAP = new ConcurrentHashMap<>();

    static {
        CHECKER_MAP.put(CommonResponseChecker.TYPE_CODE, new CommonResponseChecker());
    }


    public abstract void execute(RichServerItem item, Tuple<Integer, String> response);

}
