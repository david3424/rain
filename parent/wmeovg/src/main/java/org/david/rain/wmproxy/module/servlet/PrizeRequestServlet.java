package org.david.rain.wmproxy.module.servlet;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.util.IpUtils;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageProducer;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.david.rain.wmproxy.module.util.ExceedTotalCountException;
import org.david.rain.wmproxy.module.util.SignatureUtil;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * @ClassName PrizeRequestServlet
 * @Description 客户端请求接口
 * @date 2010-8-5 下午05:59:42
 */
public class PrizeRequestServlet extends HttpServlet {
    protected Logger log = LoggerFactory.getLogger(getClass());
    private ClientInfoMng clientInfoMng;
    private static final String CLIENTINFO_BEAN_NAME = "clientInfoMngImpl";
    private WhiteListMng whiteListMng;
    private static final String WHITELIST_BEAN_NAME = "whiteListMngImpl";

    private RequestMessageProducer requestMessageProducer;
    private static final String REQUESTMESSAGEPRODUCER_BEAN_NAME = "requestMessageProducer";

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 初始化BEAN
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        clientInfoMng = ctx.getBean(CLIENTINFO_BEAN_NAME, ClientInfoMng.class);
        whiteListMng = ctx.getBean(WHITELIST_BEAN_NAME, WhiteListMng.class);
        requestMessageProducer = ctx.getBean(REQUESTMESSAGEPRODUCER_BEAN_NAME, RequestMessageProducer.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String cid = req.getParameter("cid");
        String appid = req.getParameter("appid");
        String gid = req.getParameter("gid");
        int zoneid = Integer.parseInt(req.getParameter("zoneid"));
        long roleid = Long.parseLong(req.getParameter("roleid"));
        String account = req.getParameter("account");
//        String rolename = req.getParameter("rolename");
        int prizeid = Integer.parseInt(req.getParameter("prizeid"));
        int count = Integer.parseInt(req.getParameter("count"));
        byte priority = Byte.valueOf(req.getParameter("priority"));
        String callback = req.getParameter("callback");

        // 验证数据完整性
        if (cid == null || cid.trim().length() != 2 ||
                appid == null || appid.trim().length() == 0 ||
                gid == null || gid.trim().length() != 48 ||
                zoneid <= 0 || prizeid <= 0 || count <= 0 || (priority != 0 && priority != 1 && priority != 2) ||
                account == null || account.trim().length() <= 0) {
            log.info("流水号[{}]：数据不合法", gid);
            resp.setStatus(7);
            return;
        }

        PrizeLog prizeLog = new PrizeLog();
        prizeLog.setCid(cid.trim());
        prizeLog.setGid(gid.trim());
        prizeLog.setAppid(appid.trim());
        prizeLog.setZoneid(zoneid);
        prizeLog.setAccount(account.trim());
        prizeLog.setPrizeid(prizeid);
        prizeLog.setCount(count);
        prizeLog.setPriority(priority);
        prizeLog.setCallback(callback);
        prizeLog.setRoleid(roleid);
        prizeLog.setPrizeResendCount((byte) 0);
        prizeLog.setCallbackCount((byte) 0);
        prizeLog.setProcessCount(0);
        // 设置请求时间
        prizeLog.setRequestTime(new Date());
        // ip
        prizeLog.setIp(IpUtils.gerRealIp(req));

        // callback解码
        try {
            callback = SignatureUtil.decode(prizeLog.getCallback());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("流水号[{}]：callback解码错误；{}", prizeLog.getGid(), prizeLog.getCallback());
            resp.setStatus(6);
            return;
        }
        System.out.println("prizeLog=" + prizeLog.toString());

        ClientInfo clientInfo = clientInfoMng.getClientInfoByCid(prizeLog.getCid());

        if (null == clientInfo) {
            log.info("流水号[{}]：请求不合法； 客户端标识错误({})", prizeLog.getGid(), prizeLog
                    .getCid());
            resp.setStatus(2);
            return;
        }

        String privateKey = clientInfo.getPrivateKey();

        // 验证请求合法性
        try {
            if (!SignatureUtil.isPrizeLegal(prizeLog, req.getParameter("sign"), privateKey)) {
                log.info("流水号[{}]：请求不合法", prizeLog.getGid());
                resp.setStatus(1);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("流水号[{}]：验证数据合法性异常", prizeLog.getGid());
            resp.setStatus(5);
            return;
        }

        // 验证是否属于白名单
        int whiteListId = 0;
        try {
            whiteListId = whiteListMng.getWhiteListIdByPrizeLog(clientInfo.getId(), appid.trim(), prizeLog.getPrizeid());
        } catch (Exception e) {
            log.info("流水号[{}]：白名单验证失败", prizeLog.getGid());
            resp.setStatus(9);
            return;
        }
        if (whiteListId == 0) {
            log.info("流水号[{}]：发送物品({})不合法(3)", prizeLog.getGid(), prizeLog.getPrizeid());
            resp.setStatus(10);
            return;
        } else if (whiteListId == -1) {
            log.info("流水号[{}]：发送物品不合法(物品白名单兑换请求被拒绝)", prizeLog.getGid());
            resp.setStatus(11);
            return;
        } else if (whiteListId == -2) {
            log.info("流水号[{}]：发送物品不合法(客户端({})兑换请求被拒绝)", prizeLog.getGid(), clientInfo.getName());
            resp.setStatus(12);
            return;
        }
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
            requestMessageProducer.send(prizeLog, whiteListId);
        } catch (ExceedTotalCountException e) {
            log.info("流水号[{}]：请求失败，超过发送总数", prizeLog.getGid());
            resp.setStatus(4);
            return;
        } catch (HibernateException e) {
            log.error("流水号[{}]：更新发送总数、保存兑奖记录失败", prizeLog.getGid());
            resp.setStatus(8);
            return;
        } catch (JmsException e) {
            log.error("流水号[{}]：进入JMS消息队列失败", prizeLog.getGid());
            resp.setStatus(3);
            return;
        }
        log.info("流水号[{}]：成功进入JMS消息队列", prizeLog.getGid());
        resp.setStatus(0);
    }

    private static final long serialVersionUID = 1L;
}
