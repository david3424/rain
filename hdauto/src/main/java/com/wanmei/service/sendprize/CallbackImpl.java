package com.wanmei.service.sendprize;

import com.wanmei.common.DateUtils;
import com.wanmei.entity.PrizeTableBean;
import com.wanmei.entity.SendProperty;
import com.wanmei.logservice.OperationLog;
import com.wanmei.logservice.OperationLogger;
import com.wanmei.logservice.SendPrizeBean;
import com.wanmei.service.task.SendPropertyService;
import com.wanmei.util.SpringContextSupport;
import com.wanmei.wmeovg.callback.servlet.ICallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.sql.SQLException;

/**
 * @author liaomin  modified by davidxu
 * @version 1.0
 * @ClassName Callback
 * @Description 客户端回调消息处理
 * @date 2010-8-11 下午01:52:44
 */
public class CallbackImpl implements ICallbackService {
    private static Logger logger = LoggerFactory.getLogger(CallbackImpl.class);

    public void receive(String gid, String cid, String appid, int status, int count, String callback) throws Exception {
        // 此处代码由客户端完成
        String[] params = callback.split("&");
        String tbName = params[0];

        String dataSource = params[3];
        boolean isCoupon = false;


        if (params.length >= 5) {
            if (params[4].equals("COUPON") || params[4].equals("COUPON_MULTI")) {
                isCoupon = true;
            }

        }

        PrizeTableBean prizeTableBean = null;
        if (!tbName.startsWith("kefu_sendprize_temp")) {//不是大批量补发
            String note = params[2];
            if (note.equals("ROLEID")) {//新roleid发奖
                AwardPrizesService awardPrizesService = new AwardPrizesService(dataSource, null);
                awardPrizesService.setTableName(tbName);
                //id 有可能不为int 需检查
                int id = Integer.parseInt(params[1]);
                prizeTableBean = awardPrizesService.gainPrizeBean(tbName, id);
                if (status == 0) {
                    // 发送成功
                    if (isCoupon) {
                        awardPrizesService.updateStatus(id, 1, DateUtils.getCurrentFormatDateTime());
                    } else {
                        awardPrizesService.updateStatus(id, 1);
                    }

                } else {
                    awardPrizesService.updateStatus(id, 3);
                }

                awardPrizesService.addLog(prizeTableBean.getUsername(), prizeTableBean.getServer(),
                        Long.parseLong(prizeTableBean.getRoleid()), prizeTableBean.getPrize(), status, id, 1, gid);
            }
        } else {//丹丹用 大批量补发
            AwardPrizesService awardPrizesService = new AwardPrizesService("huodong161", null);
            awardPrizesService.setTableName(tbName);
            //id 有可能不为int 需检查
            int id = Integer.parseInt(params[1]);
            prizeTableBean = awardPrizesService.gainPrizeBean(tbName, id);
            if (status == 0) {
                // 发送成功
                awardPrizesService.updateStatus(id, 1);
            } else {
                // 发送失败
                awardPrizesService.updateRidAndStatus(id, 3, status);
            }
            try {
                awardPrizesService.addLog(prizeTableBean.getUsername(), prizeTableBean.getServer(),
                        Long.parseLong(prizeTableBean.getRoleid()), prizeTableBean.getPrize(), status, id, 1, gid);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
//        logHDBase(tbName, dataSource, prizeTableBean, status);
    }

    private void logHDBase(String tableName, String dataSource, PrizeTableBean prizeTableBean, Integer status) throws Exception {
        SendPropertyService sendPropertyService = SpringContextSupport.getSpringBean("sendPropertyService", SendPropertyService.class);
        SendProperty sendProperty = sendPropertyService.querySendProperty(tableName, dataSource);

        OperationLogger operationLogger = SpringContextSupport.getSpringBean("operationLogger", OperationLogger.class);
        Integer resultStatus = 0;
        if (status == 0) {
            resultStatus = 1;
        }
        SendPrizeBean sendPrizeBean = new SendPrizeBean();
        sendPrizeBean.setServer(prizeTableBean.getServer());
        sendPrizeBean.setUserid(prizeTableBean.getUserid() == null ? 0L : prizeTableBean.getUserid().longValue());
        sendPrizeBean.setUsername(prizeTableBean.getUsername());
        sendPrizeBean.setRoleid(Long.parseLong(prizeTableBean.getRoleid()));
        sendPrizeBean.setGid(prizeTableBean.getGid());
        sendPrizeBean.setPrize(prizeTableBean.getPrize());
        operationLogger.log(OperationLog.HandleType.SEND_PRIZE_CALLBACK.value, sendProperty, sendPrizeBean, resultStatus, status);
    }

    public static void main(String[] args) throws Exception {
    }


    public void init(ServletConfig servletConfig) throws ServletException {
    }
}