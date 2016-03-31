package org.david.rain.monitor.monitor.job;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.david.rain.monitor.monitor.shiro.MyRealm;
import org.quartz.JobKey;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by david
 * * on 13-12-30.
 */
public class MonitorConst {


    public static int SERVER_UNREACHABLE = -1;

    /*itemid与job-key对应map*/
    public static Map<Integer, JobKey> jobKeyMap = new ConcurrentHashMap<>();
    public static Map<Integer, JobKey> dataJobKeyMap = new ConcurrentHashMap<>();
    public static Map<Integer, JobKey> sendPrizeJobKeyMap = new ConcurrentHashMap<>();

    public static enum InfoLevel {
        ERROR(4),
        WARN(3),
        INFO(2),
        HEALTH(1);
        public final int value;

        private InfoLevel(int value) {
            this.value = value;
        }
    }

    public static enum JobStatus {
        STOP(0),
        RUNNING(1);
        public final int value;

        private JobStatus(int value) {
            this.value = value;
        }
    }

    public static Map<Integer, String> INFO_LEVEL_MAP = new HashMap<>();

    static {
        INFO_LEVEL_MAP.put(InfoLevel.ERROR.value, "错误");
        INFO_LEVEL_MAP.put(InfoLevel.WARN.value, "预警");
        INFO_LEVEL_MAP.put(InfoLevel.INFO.value, "提示");
        INFO_LEVEL_MAP.put(InfoLevel.HEALTH.value, "健康");
    }

    public static Map<Integer, String> NORMAL_EXCEPTION = new HashMap<>();

    static {
        NORMAL_EXCEPTION.put(2, "异常");
        NORMAL_EXCEPTION.put(1, "正常");
    }


    public static final Map<Integer, String> REMIND_MAP = new HashMap<>();

    public static int REMIND_TYPE_MESSAGE = 1;
    public static int REMIND_TYPE_EMAIL = 2;


    static {
        REMIND_MAP.put(REMIND_TYPE_MESSAGE, "短信");
        REMIND_MAP.put(REMIND_TYPE_EMAIL, "邮件");
    }

    public static final Map<Integer, String> JOB_STATUS_MAP = new HashMap<>();

    static {
        JOB_STATUS_MAP.put(JobStatus.RUNNING.value, "运行");
        JOB_STATUS_MAP.put(JobStatus.STOP.value, "停止");
    }

    public static String MESSAGE_KEY = "EVENT_MONITOR";

    public static MyRealm.ShiroUser getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        return (MyRealm.ShiroUser) currentUser.getPrincipals().getPrimaryPrincipal();
    }

}
