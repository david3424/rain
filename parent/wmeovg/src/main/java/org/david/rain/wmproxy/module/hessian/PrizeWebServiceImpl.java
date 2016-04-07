package org.david.rain.wmproxy.module.hessian;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.david.rain.webservice.ServiceManage;
import org.david.rain.wmeovg.request.alg.Signature;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.PrizeSendType;
import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageProducer;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageBrowser;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageProducer;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.david.rain.wmproxy.module.util.ExceedTotalCountException;
import org.david.rain.wmproxy.module.util.HessianUtils;
import org.david.rain.wmproxy.module.util.SignatureUtil;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;


/**
 * @version 1.0
 * @ClassName PrizeServiceImpl
 * @Description
 * @date 2010-8-13 下午03:47:14
 */
public class PrizeWebServiceImpl implements IPrizeWebService {

    protected Logger log = LoggerFactory.getLogger(getClass());


    public List<PrizeLog> queryByAccount(String account) {

        return prizeLogMng.queryByAccount(account);
    }

    public PrizeLog queryByGid(String gid) {

        return prizeLogMng.queryByGid(gid);
    }


    public List<PrizeLog> queryByRequestTime(Date start, Date end) {

        return prizeLogMng.queryByRequestTime(start, end);
    }

    public int send(String cid, String appid, String gid, Integer zoneid, String account,
                    String rolename, Integer prizeid, Integer count, Byte priority,
                    String callback, String sign, String ip) {

        String clientIp = HessianUtils.getClientIp();

        // 验证数据完整性
        if (cid == null || cid.trim().length() != 2 ||
                appid == null || appid.trim().length() == 0 ||
                gid == null || gid.trim().length() != 48 ||
                zoneid <= 0 || prizeid <= 0 || count <= 0 || (priority != 0 && priority != 1 && priority != 2) ||
                account == null || account.trim().length() <= 0 ||
                rolename == null || rolename.length() <= 0) {

            log.info("客户端[" + clientIp + "]：流水号[{}]：数据不合法", gid);

            return 7;
        }

        return commonSend(cid, appid, gid, zoneid, account, rolename, null,
                prizeid, count, priority, callback, sign, ip, clientIp, PrizeSendType.ROLENAME, null, null);
    }

    public int sendByMZRoleId(String cid, String appid, String gid, Integer zoneid, String account,
                              Long roleid, Integer prizeid, Integer count, Byte priority,
                              String callback, String sign, String ip) {

        String clientIp = HessianUtils.getClientIp();

        // 验证数据完整性
        if (cid == null || cid.trim().length() != 2 ||
                appid == null || appid.trim().length() == 0 ||
                gid == null || gid.trim().length() != 48 ||
                zoneid <= 0 || prizeid <= 0 || count <= 0 || (priority != 0 && priority != 1 && priority != 2) ||
                account == null || account.trim().length() <= 0 ||
                roleid == null || roleid <= 0) {

            log.info("客户端[" + clientIp + "]：流水号[{}]：数据不合法", gid);

            return 7;
        }

        return commonSend(cid, appid, gid, zoneid, account, null, roleid,
                prizeid, count, priority, callback, sign, ip, clientIp, PrizeSendType.MZ_ROLEID, null, null);
    }

    public int sendByRoleIdWithTitleAndContent(String cid, String appid, String gid, Integer zoneid, String account,
                                               Long roleid, Integer prizeid, Integer count, Byte priority,
                                               String callback, String sign, String ip, String title, String content) {

        String clientIp = HessianUtils.getClientIp();

        // 验证数据完整性
        if (cid == null || cid.trim().length() != 2 ||
                appid == null || appid.trim().length() == 0 ||
                gid == null || gid.trim().length() != 48 ||
                zoneid <= 0 || prizeid <= 0 || count <= 0 || (priority != 0 && priority != 1 && priority != 2) ||
                account == null || account.trim().length() <= 0 ||
                roleid == null || roleid <= 0) {

            log.info("客户端[" + clientIp + "]：流水号[{}]：数据不合法", gid);

            return 7;
        }

        return commonSend(cid, appid, gid, zoneid, account, null, roleid,
                prizeid, count, priority, callback, sign, ip, clientIp, PrizeSendType.MZ_ROLEID_WITHTITLEANDCONTENT
                , title, content);
    }

    /**
     * 除梦珠外的其他游戏调用
     */
    public int sendByRoleId(String cid, String appid, String gid, Integer zoneid, String account,
                            Integer roleid, Integer prizeid, Integer count, Byte priority,
                            String callback, String sign, String ip) {

        String clientIp = HessianUtils.getClientIp();

        // 验证数据完整性
        if (cid == null || cid.trim().length() != 2 ||
                appid == null || appid.trim().length() == 0 ||
                gid == null || gid.trim().length() != 48 ||
                zoneid <= 0 || prizeid <= 0 || count <= 0 || (priority != 0 && priority != 1 && priority != 2) ||
                account == null || account.trim().length() <= 0 ||
                roleid == null || roleid <= 0) {

            log.info("客户端[" + clientIp + "]：流水号[{}]：数据不合法", gid);

            return 7;
        }

        return commonSend(cid, appid, gid, zoneid, account, null, Long.valueOf(roleid),
                prizeid, count, priority, callback, sign, ip, clientIp, PrizeSendType.ROLEID, null, null);
    }

    public int commonSend(String cid, String appid, String gid, Integer zoneid, String account,
                          String rolename, Long roleid, Integer prizeid, Integer count, Byte priority,
                          String callback, String sign, String ip, String clientIp, Byte prizeSendType, String title, String content) {

        PrizeLog prizeLog = new PrizeLog();
        prizeLog.setCid(cid.trim());
        prizeLog.setGid(gid.trim());
        prizeLog.setAppid(appid.trim());
        prizeLog.setZoneid(zoneid);
        prizeLog.setAccount(account.trim());
        prizeLog.setRolename(rolename);
        prizeLog.setRoleid(roleid);
        prizeLog.setPrizeid(prizeid);
        prizeLog.setCount(count);
        prizeLog.setPriority(priority);
        prizeLog.setCallback(callback);

        prizeLog.setPrizeResendCount((byte) 0);
        prizeLog.setCallbackCount((byte) 0);
        prizeLog.setProcessCount(0);
        // 设置请求时间
        prizeLog.setRequestTime(new Date());
        // ip
        prizeLog.setIp(ip);

        prizeLog.setPrizeSendType(prizeSendType);

        prizeLog.setTitle(title);
        prizeLog.setContent(content);

        // 角色名和callback解码
        try {
            if ((rolename = StringUtils.trimToNull(rolename)) != null)
                rolename = SignatureUtil.decode(prizeLog.getRolename());
            callback = SignatureUtil.decode(prizeLog.getCallback());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("客户端[" + clientIp + "]：流水号[{}]：角色名或callback解码错误；{}", prizeLog.getGid(),
                    prizeLog.getRolename() + "  " + prizeLog.getCallback());
            return 6;
        }

        //System.out.println("prizeLog=" + prizeLog.toString());

        ClientInfo clientInfo = clientInfoMng.getClientInfoByCid(prizeLog
                .getCid());

        if (null == clientInfo) {
            log.error("客户端[" + clientIp + "]：流水号[{}]：请求不合法； 客户端标识错误({})", prizeLog.getGid(), prizeLog
                    .getCid());
            return 2;
        }

        String legalClientIp = clientInfo.getClientIp();

        Pattern p = Pattern.compile(legalClientIp);
        Matcher m = p.matcher(clientIp.trim());

        if (StringUtils.isBlank(legalClientIp)
                || StringUtils.isBlank(clientIp)
                //|| !legalClientIp.trim().equalsIgnoreCase(clientIp.trim())){
                || !m.find()) {

            log.warn("客户端[" + clientIp + "]：流水号[{}]：请求不合法； 客户端IP不符({})", prizeLog.getGid(), legalClientIp);

            return 13;
        }

        String privateKey = clientInfo.getPrivateKey();

        // 验证请求合法性
        try {
            if (!SignatureUtil.isPrizeLegal(prizeLog, sign, privateKey)) {
                log.info("客户端[" + clientIp + "]：流水号[{}]：请求不合法", prizeLog.getGid());
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("客户端[" + clientIp + "]：流水号[{}]：验证数据合法性异常", prizeLog.getGid());
            return 5;
        }

        // 验证是否属于白名单
        int whiteListId = 0;
        try {
            whiteListId = whiteListMng.getWhiteListIdByPrizeLog(clientInfo.getId(), appid.trim(), prizeLog.getPrizeid());
        } catch (Exception e) {

            log.info("客户端[" + clientIp + "]：流水号[{}]：白名单验证失败", prizeLog.getGid());
            return 9;
        }
        if (whiteListId == 0) {
            log.info("客户端[" + clientIp + "]：流水号[{}]：发送物品({})不合法", prizeLog.getGid(),
                    " cid=" + clientInfo.getCid() + " appid=" + appid.trim() + " prizeid=" + prizeLog.getPrizeid());
            return 10;
        } else if (whiteListId == -1) {
            log.info("客户端[" + clientIp + "]：流水号[{}]：发送物品不合法(物品白名单兑换请求被拒绝)", prizeLog.getGid());
            return 11;
        } else if (whiteListId == -2) {
            log.info("客户端[" + clientIp + "]：流水号[{}]：发送物品不合法(客户端({})兑换请求被拒绝)", prizeLog.getGid(), clientInfo.getName());
            return 12;
        }

        prizeLog.setRolename(rolename);
        prizeLog.setCallback(callback);

        // 初始化奖品兑换记录状态
        prizeLog.setSendStatus((byte) 10);
        prizeLog.setCallbackStatus((byte) 10);

        // 计算发送优先级
        Byte clientPriority = clientInfo.getPriority();
        Byte prizePriority = prizeLog.getPriority();
        if (prizePriority >= 2) {
            prizeLog.setPriority((byte) 9);
        } else {
            prizeLog.setPriority((byte) ((clientPriority << 1) ^ prizePriority));
        }

        // 更新已发送总数，将兑奖记录保存至数据库, 并置入JMS消息队列。（同一事务中）
        try {

//            log.error("[{}] 111 开始进入请求队列 时间：[{}]", prizeLog.getGid(), System.currentTimeMillis());
            requestMessageProducer.send(prizeLog, whiteListId);

        } catch (ExceedTotalCountException e) {

            log.info("客户端[" + clientIp + "]：流水号[{}]：请求失败，超过发送总数", prizeLog.getGid());
            return 4;
        } catch (HibernateException e) {

            log.error("客户端[" + clientIp + "]：流水号[{}]：更新发送总数、保存兑奖记录失败", prizeLog.getGid());
            return 8;
        } catch (JmsException e) {

            log.error("客户端[" + clientIp + "]：流水号[{}]：进入JMS消息队列失败", prizeLog.getGid());
            return 3;
        }

        log.info("客户端[" + clientIp + "]：流水号[{}]：成功进入JMS消息队列；物品信息[{}]", prizeLog.getGid(), prizeLog.toString());
        return 0;
    }

    /**
     * 兑换记录重发时重置发送失败数目
     * 两种情况： 1、发送数目为一条（判断是否已发 ->更新发送失败白名单数量）
     * 2、发送数目为多条 (更新发送失败白名单数量 -> 调用RequestMessageProducer.resend -> 在发送奖品时读出本次要发送的物品总数 )
     */
    public int resend(String gid) throws Exception {

        String clientIp = HessianUtils.getClientIp();
        log.info("客户端[" + clientIp + "]重新发送物品  gid=" + gid);

        if (gid == null || gid.trim().length() == 0) {
            return -1;
        }

        if (requestMessageBrowser.isInQueue(gid)) {

            return -4; // 流水号已在当前请求队列
        }

        // 流水号不存在
        PrizeLog prizeLog = prizeLogMng.findById(gid);
        if (prizeLog == null) {
            return -2;
        }

        // 已经发送成功
        if (prizeLog.getSendStatus() == 0) {
            callbackMessageProducer.send(prizeLog);

            return 1;
        }

        if (prizeLog.getSendStatus() == 10 || whiteListMng.resetFailCount(prizeLog) > 0) {

            requestMessageProducer.resend(prizeLog);
        } else {

            return -5;
        }

        return 0;
    }

    // 强制使用roleid重新发奖
    public int resendByRoleId(String gid, int roleId) throws Exception {

        String clientIp = HessianUtils.getClientIp();
        log.info("客户端[" + clientIp + "]重新发送物品  gid=" + gid);

        if (gid == null || gid.trim().length() == 0) {
            return -1;
        }

        if (requestMessageBrowser.isInQueue(gid)) {

            return -4; // 流水号已在当前请求队列
        }

        // 流水号不存在
        PrizeLog prizeLog = prizeLogMng.findById(gid);
        if (prizeLog == null) {
            return -2;
        }

        // 已经发送成功
        if (prizeLog.getSendStatus() == 0) {
            callbackMessageProducer.send(prizeLog);

            return 1;
        }

        if (prizeLog.getSendStatus() == 10 || whiteListMng.resetFailCount(prizeLog) > 0) {

            prizeLog.setPrizeSendType(PrizeSendType.ROLEID);
            prizeLog.setRoleid((long) roleId);
            requestMessageProducer.resend(prizeLog);
        } else {

            return -5;
        }

        return 0;
    }

    // 强制使用roleid重新发奖
    public int resendByMZRoleId(String gid, long roleId) throws Exception {

        String clientIp = HessianUtils.getClientIp();
        log.info("客户端[" + clientIp + "]重新发送物品  gid=" + gid);

        if (gid == null || gid.trim().length() == 0) {
            return -1;
        }

        if (requestMessageBrowser.isInQueue(gid)) {

            return -4; // 流水号已在当前请求队列
        }

        // 流水号不存在
        PrizeLog prizeLog = prizeLogMng.findById(gid);
        if (prizeLog == null) {
            return -2;
        }

        // 已经发送成功
        if (prizeLog.getSendStatus() == 0) {
            callbackMessageProducer.send(prizeLog);

            return 1;
        }

        if (prizeLog.getSendStatus() == 10 || whiteListMng.resetFailCount(prizeLog) > 0) {

            prizeLog.setPrizeSendType(PrizeSendType.MZ_ROLEID);
            prizeLog.setRoleid(roleId);
            requestMessageProducer.resend(prizeLog);
        } else {

            return -5;
        }

        return 0;
    }



    public long verifyRoleExists(String username, Integer zoneid, long roleid) throws Exception {


//        byte[] rolenameBytes = Signature.decryptBASE64(encodeRolename);
//        String rolename = new String(rolenameBytes, "UTF-8");

        String clientIp = HessianUtils.getClientIp();

        long rtn = ServiceManage.serviceInterface.verifyRole(username, zoneid, roleid);
        log.info("客户端[" + clientIp + "]查询角色名  username=" + username + ";zoneid=" + zoneid + "(roleid=" + roleid + ")" + " 查询结果：" + rtn);
        return rtn;
    }

    @Autowired
    private PrizeLogMng prizeLogMng;
    @Autowired
    private WhiteListMng whiteListMng;

    @Autowired
    private RequestMessageProducer requestMessageProducer;
    @Autowired
    private CallbackMessageProducer callbackMessageProducer;
    @Autowired
    private RequestMessageBrowser requestMessageBrowser;

    @Autowired
    private ClientInfoMng clientInfoMng;
}
