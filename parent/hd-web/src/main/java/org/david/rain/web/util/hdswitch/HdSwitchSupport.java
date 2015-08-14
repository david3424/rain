package org.david.rain.web.util.hdswitch;

import org.david.rain.common.lang.Tuple;
import org.david.rain.common.util.DateUtils;
import org.david.rain.common.util.ObjectResponseWrapper;
import org.david.rain.web.util.CommonMessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class HdSwitchSupport {

    public final static Logger logger = LoggerFactory.getLogger(HdSwitchSupport.class);

    @Autowired
    TestAccountService testAccountService;
    @Autowired
    HdSwitchService hdSwitchService;


    /**
     * @param statusKey 活动开关标志
     * @param username  用户名，如果没有传空
     * @return Tuple.l false 活动不能访问，Tuple.r 一个通用的response对象
     */
    public Tuple<Boolean, Map<String, Object>> isEventOpen(String statusKey, String username) {
        if (statusKey == null) {
            throw new IllegalArgumentException("can not call this method without a status key");
        }
        HdSwitchBean hdSwitchBean = hdSwitchService.getHdSwitch(statusKey);

        if (hdSwitchBean == null) {
            return new Tuple<>(false, ObjectResponseWrapper.commonResponse(false, CommonMessageConst.EVENT_CLOSED, "活动已关闭[1]。"));
        }

        Integer hdSwitchOpenStatus = hdSwitchBean.getOpen();
        if (hdSwitchOpenStatus.equals(HdSwitchBean.SWITCH_CLOSE_STATUS)) {
            try {
                if (username != null && testAccountService.isTestAccount(username)) {
                    return new Tuple<>(true, null);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        String sBegin = hdSwitchBean.getBeginTime();
        String sEnd = hdSwitchBean.getEndTime();

        if (sBegin == null || sEnd == null) {
            sBegin = "3000-01-01 00:00:01";
            sEnd = "1980-02-02 00:00:01";
        }

        String sNow = DateUtils.getCurrentFormatDateTime();
        if (sBegin.length() != 19 || sEnd.length() != 19) {
            logger.error("switch bean :" + hdSwitchBean);
            return new Tuple<>(false, ObjectResponseWrapper.commonResponse(false, CommonMessageConst.EVENT_SWITCH_COMMON_ERROR, "活动开关异常[1]。"));
        }


        if (sNow.compareTo(sBegin) < 0) {
            return new Tuple<>(false, ObjectResponseWrapper.commonResponse(false, CommonMessageConst.EVENT_NOT_START, "活动还未开始。"));
        } else if (sNow.compareTo(sEnd) > 0) {
            return new Tuple<>(false, ObjectResponseWrapper.commonResponse(false, CommonMessageConst.EVENT_OVER, "活动已结束。"));
        } else {
            if (hdSwitchOpenStatus.equals(HdSwitchBean.SWITCH_CLOSE_STATUS))
                return new Tuple<>(false, ObjectResponseWrapper.commonResponse(false, CommonMessageConst.EVENT_MAINTAINING, "活动维护中。"));
            else {
                return new Tuple<>(true, null);
            }
        }

    }

    public Tuple<Boolean, Map<String, Object>> isEventOpen(String statusKey) {
        return isEventOpen(statusKey, null);
    }
}
