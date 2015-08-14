package org.david.rain.web.controller.demo;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class WebMallConst {


    final static String TABLE_USER = "hex_webmall_userinfo";
    final static String TABLE_lOTTERY_PRIZE = "hex_gossip_prize";
    final static String TABLE_SIGN_IN_LOG = "hex_webmall_signin_log";
    final static String TABLE_WEIXIN_ATTENTION_LOG = "hex_webmall_weixin_attention_log";
    final static String TABLE_APP_DOWNLOAD_LOG = "hex_webmall_app_download_log";
    final static String TABLE_CODE = "hex_webmall_code";
    final static String TABLE_CODE_DAYLIMIT = "hex_webmall_code_daylimit";
    final static String TABLE_CODE_OBTAIN_STATICS= "hex_webmall_code_obtain_log";

    final static String TABLE_WEBMALL_LOG = "hex_webmall_fail_log";
    static final String TABLE_WEBMALL_ANSWER = "hex_webmall_answer";


    static final String TABLE_WEBMALL_USER = "hd_webmall_interface_user";
    static final String TABLE_WEBMALL_USERSCORE = "hd_webmall_interface_score";
    static final String TABLE_WEBMALL_USERSCORE_LOG = "hd_webmall_interface_score_log";
    static final String TABLE_WEBMALL_FAIL_LOG = "hd_webmall_interface_fail_log";




    static final int EXCHANGE_CODE_NEED_STONE = 1;


    static final String WEBMALL_WEIXIN_ATTENTION_CODE = "HEXBYWXUSD8008C";
    static final String WEBMALL_APP_DOWNLOAD_CODE = "HEXBYWXUSD8008C";


    public final static int FIRST_SUBMIT = 1;
    public final static int NOT_FIRST_SUBMIT = 0;

    public final static String SIGN_IN_BEGIN_DATE = "2015-07-01";
    public final static String SIGN_IN_END_DATE = "2015-07-31";

    public static Map<String, Integer> TIP_SCORE_MAP; //每个活动对应得到得z点积分
    public static Map<String, String> TIP_HDNAME_MAP; // 获得z点的 活动名称  明细

    public static String[] answers={"B","B","B","C","A","B","A","B",};

    static {
        TIP_SCORE_MAP = new HashMap<String, Integer>();
        TIP_SCORE_MAP.put("sign", 10);
        TIP_SCORE_MAP.put("weibo", 10);
        TIP_SCORE_MAP.put("weixin", 10);
        TIP_SCORE_MAP.put("app", 10);
        TIP_SCORE_MAP.put("share", 10);
        TIP_SCORE_MAP.put("answer", 10);
        TIP_SCORE_MAP.put("Fsubmit", 10);
        TIP_SCORE_MAP.put("Fupdate", 10);
        TIP_SCORE_MAP.put("client", 10);

        TIP_HDNAME_MAP = new HashMap<String, String>();
        TIP_HDNAME_MAP.put("sign", "每日签到");
        TIP_HDNAME_MAP.put("weibo_sina", "关注新浪微博");
        TIP_HDNAME_MAP.put("weibo_tencnet", "关注腾讯微博");
        TIP_HDNAME_MAP.put("weixin", "关注微信");
        TIP_HDNAME_MAP.put("app", "APP下载");
        TIP_HDNAME_MAP.put("client", "内测客户端下载");
        TIP_HDNAME_MAP.put("share", "激斗内测分享");
        TIP_HDNAME_MAP.put("answer", "HEX百科问答");
        TIP_HDNAME_MAP.put("firstSubmit", "首次提交个人信息赢积分");
        TIP_HDNAME_MAP.put("firstUpdate", "首次完善个人信息赢积分");
        TIP_HDNAME_MAP.put("zmall", "Z点商城消费使用");
    }

}
