package org.david.rain.wmeovg.request.service;

import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.david.rain.wmeovg.request.bean.PrizeBean;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.hessian.IPrizeWebService;
import org.david.rain.wmeovg.request.util.Priority;
import org.david.rain.wmeovg.request.util.SignatureUtil;
import org.david.rain.wmeovg.request.util.WmeovgProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @version 1.0
 * @ClassName PrizeService
 * @Description 客户端兑换请求服务
 * @date 2010-8-5 下午06:01:19
 */
public final class PrizeServiceImpl implements IPrizeService {

    protected static Logger log = LoggerFactory.getLogger(PrizeServiceImpl.class);

    private static String cid;
    private static String serverUrl;
    private static String keystorePath;
    private static String keystorePwd;
    private static String privateKey;

    private static IPrizeWebService prizeWebService;

    static {
        cid = WmeovgProperties.getCid();
        serverUrl = WmeovgProperties.getServerUrl();
        keystorePath = WmeovgProperties.getKeystorePath();
        keystorePwd = WmeovgProperties.getKeystorePwd();
        privateKey = WmeovgProperties.getPrivateKey();

        System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
        System.setProperty("javax.net.ssl.keyStoreType", "jks");
        System.setProperty("javax.net.ssl.keyStore", keystorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keystorePwd);

        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession sslSession) {
                return urlHostName.equals(sslSession.getPeerHost());
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        try {
            String url = serverUrl + "/hessian/prizeService";
            HessianProxyFactory factory = new HessianProxyFactory();
            prizeWebService = (IPrizeWebService) factory.create(IPrizeWebService.class, url);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("虚拟物品兑换系统：webservice初始化失败", e);
        }
    }

    public String genGid() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        StringBuffer sb = new StringBuffer();
        sb.append(cid);
        sb.append("-");
        sb.append(sdf.format(new Date()));
        sb.append("-");
        sb.append(UUID.randomUUID());
        return sb.toString();
    }

    /**
     * 虚拟物品兑换请求方法
     *
     * @param prizeBean
     * @return -1(请求超时)或 HTTP响应状态码
     */
    private int send(PrizeBean prizeBean) {

        // 计算签名
        String sign;
        try {
            sign = SignatureUtil.sign(prizeBean, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("虚拟物品兑换系统：计算兑换请求签名错误：", e);
            return -1;
        }

        //MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setContentCharset("UTF-8");
        HttpConnectionManagerParams managerParams = httpClient
                .getHttpConnectionManager().getParams();
        // 设置连接超时时间(ms)
        managerParams.setConnectionTimeout(5000);
        //managerParams.setStaleCheckingEnabled(true);
        //managerParams.setDefaultMaxConnectionsPerHost(20);
        //managerParams.setMaxTotalConnections(200);


        System.setProperty("java.protocol.handler.pkgs",
                "com.sun.net.ssl.internal.www.protocol");
        System.setProperty("javax.net.ssl.keyStoreType", "jks");
        System.setProperty("javax.net.ssl.keyStore", keystorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keystorePwd);

        PostMethod postMethod = new PostMethod(serverUrl + "/servlet/prizerequest");
        // 设置请求超时时间(ms)
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
        // 奖品兑换参数
        NameValuePair[] params = {
                new NameValuePair("cid", prizeBean.getCid()),
                new NameValuePair("gid", prizeBean.getGid()),
                new NameValuePair("account", prizeBean.getAccount()),
                new NameValuePair("appid", prizeBean.getAppid()),
                new NameValuePair("roleid", prizeBean.getRoleid().toString()),
                new NameValuePair("zoneid", prizeBean.getZoneid().toString()),
                new NameValuePair("prizeid", prizeBean.getPrizeid().toString()),
                new NameValuePair("count", prizeBean.getCount().toString()),
                new NameValuePair("priority", prizeBean.getPriority().ordinal() + ""),
                new NameValuePair("callback", prizeBean.getCallback()),
                new NameValuePair("sign", sign)};
        postMethod.setQueryString(params);
        int statusCode = -1;
        try {
            statusCode = httpClient.executeMethod(postMethod);
        } catch (Exception ex) {
            log.error("虚拟物品兑换系统：HTTP请求错误（服务器未响应）。流水号{}", prizeBean.getGid());
            ex.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return statusCode;
    }

    public List<PrizeLog> queryByAccount(String account) {

        return prizeWebService.queryByAccount(account);
    }


    public PrizeLog queryByGid(String gid) {

        return prizeWebService.queryByGid(gid);
    }


    public List<PrizeLog> queryByRequestTime(Date start, Date end) {

        return prizeWebService.queryByRequestTime(start, end);
    }

    public int send(String gid, Integer zoneid, String account,
                    String appid, Long roleid, Integer prizeid, String callback) {
        PrizeBean prizeBean = null;
        try {
            prizeBean = new PrizeBean(gid, zoneid, account, appid, roleid, prizeid, callback);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("虚拟物品兑换系统：兑换请求生成错误");
        }
        return send(prizeBean);
    }

    public int send(String gid, Integer zoneid, String account,
                    String appid, Long roleid, Integer prizeid, Integer count, Priority priority,
                    String callback) {

        PrizeBean prizeBean = null;
        try {
            prizeBean = new PrizeBean(gid, zoneid, account, appid, roleid, prizeid, count, priority, callback);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("虚拟物品兑换系统：兑换请求生成错误");
        }

        return send(prizeBean);
    }
}
