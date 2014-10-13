package com.wanmei.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Constant {
    public static final String TRIGGERNAME = "triggerName";
    public static final String TRIGGERGROUP = "triggerGroup";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String REPEATCOUNT = "repeatCount";
    public static final String REPEATINTERVEL = "repeatInterval";
    public static final String TABLE_SEND_PRIZE_OPEN = "hd_sendprize_open";
    public final static String TABLE_GAME_PRIZE_LOGS_NEW = "auto_game_prize_roleid_newlogs_new";
    public final static String TABLE_GAME_RESEND_PRIZE_ORDER = "hd_sendprize_user";
    public final static String TABLE_GAME_RESEND_PRIZE_FAILED = "kefu_sendprize_failed";

    public static final Map<String, String> status = new HashMap<String, String>();

    static {
        status.put("ACQUIRED", "运行中");
        status.put("PAUSED", "暂停中");
        status.put("WAITING", "等待中");
    }


    public static final Set<String> SUPPORT_IMPORT_FILE_TYPE = new HashSet<String>();


    public static final String IMPORT_FILE_PATH = Constant.class.getResource("").getPath() + "upload/";

    static {
        SUPPORT_IMPORT_FILE_TYPE.add("text/plain");
        SUPPORT_IMPORT_FILE_TYPE.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
