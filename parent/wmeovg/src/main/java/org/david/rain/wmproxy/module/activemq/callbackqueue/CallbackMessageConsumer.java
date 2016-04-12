package org.david.rain.wmproxy.module.activemq.callbackqueue;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.david.rain.wmeovg.request.alg.Signature;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.SignatureUtil;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.david.rain.wmproxy.module.util.FailToCallbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ClassName CallbackMessageConsumer
 * @Description 客户端回调消息消费者，通知客户端兑换结果
 * @date 2010-8-5 下午05:57:13
 */
public class CallbackMessageConsumer {

    MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    //HttpClient httpClient = new HttpClient(connectionManager);

    ConcurrentHashMap<String, HttpClient> httpClientMap = new ConcurrentHashMap<String, HttpClient>();

    protected Logger log = LoggerFactory.getLogger(getClass());

    private HttpClient getHttpClientByHost(String host) {
        HttpClient client = httpClientMap.get(host);
        if (client == null) {
            client = new HttpClient(connectionManager);
            client = httpClientMap.putIfAbsent(host, client);
        }
        return client;
    }

    public void receive(ObjectMessage message) throws FailToCallbackException {
        PrizeLog prizeLog = null;
        try {
            prizeLog = (PrizeLog) message.getObject();

        } catch (JMSException e) {

            log.error("JMS接收消息错误：", e);
            return;
        }

        String gid = prizeLog.getGid();
        Byte sendStatus = prizeLog.getSendStatus();
        Integer processCount = prizeLog.getProcessCount();
        String callback = prizeLog.getCallback();
        String cid = prizeLog.getCid();

        ClientInfo clientInfo = clientInfoMng.getClientInfoByCid(prizeLog.getCid());

        if (null == clientInfo) {
            log.warn("流水号[{}]：客户端回调不合法； 客户端标识错误({})", prizeLog.getGid(), prizeLog.getCid());
            return;
        }

        String privateKey = clientInfo.getPrivateKey();
        String sign = null;
        try {
            sign = SignatureUtil.sign(gid.trim() + sendStatus + callback.trim() + privateKey.trim());
        } catch (NoSuchAlgorithmException e1) {

            log.error("客户端回调签名异常=============", e1);
        }

        String host = clientInfo.getRootUrl();
        //String callbackUrl = host + "/servlet/wmeovg/callback";

        HttpClient httpClient;
        httpClient = getHttpClientByHost(host);
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        // 设置连接超时时间(ms)
        managerParams.setConnectionTimeout(15000);
        PostMethod postMethod = new PostMethod(host);
        // 设置请求超时时间(ms)
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 15000);
//        log.info("++++++++++++==============callback==" + new String(Signature.decryptBASE64(Signature.encryptBASE64(callback.getBytes()))));
        String callb = null;
        try {
            callb = Signature.encryptBASE64(callback.getBytes("UTF-8"));
            log.info("++++++++++++==============callback=={}", callb);
        } catch (Exception e1) {
            log.error("客户端回调callback解码异常=============", e1);
        }
        // 奖品兑换参数
        NameValuePair[] params = {new NameValuePair("gid", gid),
                new NameValuePair("cid", cid),
                new NameValuePair("appid", prizeLog.getAppid()),
                new NameValuePair("status", sendStatus.toString()),
                new NameValuePair("count", processCount.toString()),
                new NameValuePair("callback", callb),
                new NameValuePair("sign", sign)};

        postMethod.setQueryString(params);
        int statusCode = -1;
        try {
            statusCode = httpClient.executeMethod(postMethod);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("流水号[{}]：客户端接口HTTP请求错误（客户端{}未响应）", gid, cid);
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }

        if (statusCode != HttpStatus.SC_OK) {
            prizeLogMng.accumulateCallbackCountByGID(gid);

            try {
                Thread.sleep(2000); // 等待2秒重新请求
            } catch (InterruptedException e) {

                log.error("客户端回调线程等待异常=============", e);
            }

            log.warn("流水号[" + gid + "]：客户端(" + cid + ")接口调用失败, HTTP状态码：{}", statusCode);

            throw new FailToCallbackException();
        }

        // 更新客户端接口调用时间及状态
        prizeLog.setCallbackHttpStatus(statusCode);
        prizeLog.setCallbackTime(new Date());

        prizeLogMng.updateCallbackStatusByGID(gid, statusCode, prizeLog.getCallbackTime());
        log.warn("流水号[{}]：完成客户端接口调用(" + statusCode + ")", gid);
    }

    @Autowired
    private PrizeLogMng prizeLogMng;
    @Autowired
    private ClientInfoMng clientInfoMng;
}
