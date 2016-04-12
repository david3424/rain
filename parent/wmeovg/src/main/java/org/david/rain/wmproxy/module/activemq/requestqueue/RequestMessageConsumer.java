package org.david.rain.wmproxy.module.activemq.requestqueue;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.commons.lang.StringUtils;
import org.david.rain.webservice.ServiceManage;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.PrizeSendType;
import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageProducer;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.david.rain.wmproxy.module.util.FailToSendPrizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;

/**
 * @version 1.0
 * @ClassName RequestMessageConsumer
 * @Description 兑换请求消息消费者，调用接口，并将消息置入客户端回调消息队列
 * @date 2010-8-5 下午05:58:33
 */
public class RequestMessageConsumer {

    protected Logger log = LoggerFactory.getLogger(getClass());

    public void receive(ObjectMessage message) throws FailToSendPrizeException {

        PrizeLog prizeLog = null;
        try {
            prizeLog = (PrizeLog) message.getObject();

        } catch (JMSException e) {

            log.error("JMS接收消息错误：", e);
            return;
        }

//        log.error("[{}] 222 进入请求队列 receive  时间：[{}]", prizeLog.getGid(), System.currentTimeMillis());

        Byte prizeSendType = prizeLog.getPrizeSendType();
        if (null == prizeSendType) {
            prizeSendType = PrizeSendType.ROLEID;
        }
        String gid = prizeLog.getGid();
        String cid = prizeLog.getCid();
        String appid = prizeLog.getAppid();
        String account = prizeLog.getAccount();
        int zoneid = prizeLog.getZoneid();
        Long roleid = prizeLog.getRoleid();
        int prizeid = prizeLog.getPrizeid();
        int count = prizeLog.getCount();
        int status = -5;
        int processCount = prizeLog.getProcessCount(); // 已处理个数

        // 验证是否属于白名单
        byte whiteListStatus = -10;
        String emailTile = "";     //邮件标题
        String emailText = "";     //邮件正文
        //是否发送奖品邮件
        boolean emailInterface = false;
        //whiteListStatus = whiteListMng.getWhiteListStatus(cid, appid, prizeid);
        //原来只是查状态，先在查询要发送的邮件标题和邮件内容，o[0] 还是原来的状态 o[2] o[3] 是邮件标题和内容
        Object[] o = whiteListMng.getWhiteListStatusAndEmailInfo(cid, appid, prizeid);
        whiteListStatus = (Byte) o[0];
        if (o[2] != null && o[3] != null && !StringUtils.isEmpty((String) o[2]) && !StringUtils.isEmpty((String) o[3])) {
            emailTile = (String) o[2];
            emailText = (String) o[3];
            emailInterface = true;
        }
        if (whiteListStatus == 0) { //白名单通过
            if (count == 1 && prizeLog.getSendStatus() != 0) { //没有发成功过
                try {
                    if (emailInterface) {
                        log.info("(新邮件发奖接口)准备发送奖品({})", prizeLog.toString());
                        status = ServiceManage.serviceInterface.sendEmailToRole(account, zoneid, roleid, prizeid, emailTile, emailText);
                    } else {
                        log.info("error in sendemail interface");
                    }
                } catch (Exception e) {
                    log.error("error：", e);
                    status = -5;
                }
                processCount = count;
                /*网络错误 或者调用接口异常*/
                if (-1 == status || -5 == status) {
                    //resendcount +1
                    prizeLogMng.accumulatePrizeResendCountByGID(gid);
                    try {
                        Thread.sleep(2000); // 等待2秒重新发送
                    } catch (InterruptedException e) {
                        log.error("客户端发奖等待异常=============", e.getMessage());
                    }
                    int resendCount = prizeLogMng.getPrizeResendCountByGID(gid);
                    if (resendCount % 3 == 0) {
                        // 发送失败提示客户端
                        process(prizeLog, status, processCount);
                        log.warn("流水号[{}]：发送失败({})", gid, status);
                        return;
                    } else {
                        log.warn("流水号[{}]：发送奖品错误（其他错误，很可能是网络通讯错误），将重新发送({})", gid, status);
                        throw new FailToSendPrizeException(); //用到了jms事务，立即进入队列 重新发送
                    }
                }
            } else if (count > 1) {
                count = count - processCount; // 计算当前要发送的数目（用于物品重发个数计算）
                if (prizeLog.getSendStatus() != 10) {
                    prizeLogMng.accumulatePrizeResendCountByGID(gid);
                }
                while (--count >= 0) {
                    try {
                        if (emailInterface) {
                            log.info("(新邮件发奖接口)准备发送奖品({})", prizeLog.toString());
                            status = ServiceManage.serviceInterface.sendEmailToRole(account, zoneid, roleid, prizeid, emailTile, emailText);
                        } else {
                            log.info("(未知类型{})准备发送奖品失败({})", prizeSendType, prizeLog.toString());
                            status = -5;
                            break;
                        }
                    } catch (Exception e) {
                        log.error("接口调用错误：", e.getMessage());
                        status = -5;
                    }
                    if (status == 0)
                        processCount++;
                    else
                        break;
                    log.info("流水号[{}]：第{}个奖品发送成功", gid, processCount);
                }
            }
        } else if (whiteListStatus == 1) {
            status = -2; // 当前应用下的物品兑换请求处于拒绝状态
        } else if (whiteListStatus == 2) {
            status = -3; // 客户端兑换请求处于拒绝状态
        } else {
            status = -4; // 数据库读取错误：白名单验证失败
        }
        process(prizeLog, status, processCount);
    }

    public void process(PrizeLog prizeLog, int status, int processCount) {
        // 更新兑奖记录发送状态和处理时间
        prizeLog.setSendStatus((byte) status);
        prizeLog.setSendTime(new Date());
        prizeLog.setProcessCount(processCount);

        // 更新发送失败个数，并更新兑换记录发送状态、发送时间、处理个数
        try {
            whiteListMng.accumulateFailCount(prizeLog);
        } catch (Exception e) {

            log.error("接口调用完成后更新数据库失败", e.getMessage());
        }
        log.info("流水号[{}]：完成接口调用(" + status + ")", prizeLog.getGid());
        try {
            callbackMessageProducer.send(prizeLog);
        } catch (JmsException e) {
            log.info("流水号[{}]：进入回调队列失败", prizeLog.getGid());
        }
    }

    @Autowired
    private PrizeLogMng prizeLogMng;

    @Autowired
    private CallbackMessageProducer callbackMessageProducer;

    @Autowired
    private WhiteListMng whiteListMng;
}
