/*
*  时间： 2007-8-17 11:30:08
*  北京完美时空网络技术有限公司
*/
package com.david.web.pppppp.service.webservice;

import com.caucho.hessian.client.HessianProxyFactory;
import com.david.web.pppppp.service.logservice.ILogService;
import com.david.web.pppppp.service.webservice.textmessage.server.webservice.WSTextMessageService;
//import common.CouponServiceInterface;

/**
 * Created by IntelliJ IDEA.
 * 作者: 李亮阳 david modified
 * 日期: 2007-8-17
 * 时间: 11:30:08
 * 版本: 1.0
 * 类说明:完美时空 游戏活动管理平台
 * <p/>
 * <p/>
 * 该平台的hessian版本叫低，所以没有和spring集成
 */
public class ServiceManage {

    public static WSTextMessageService wsTextMessageService = null;

    public static ILogService logService = null;


    static {
        try {
            initial();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServiceManage() {
    }

    private static void initial() {
        try {
            String wsmessageurl = "http://61.152.110.228/text-message-server/ws/message";
            HessianProxyFactory factory = new HessianProxyFactory();

            wsTextMessageService = (WSTextMessageService) factory.create(WSTextMessageService.class, wsmessageurl);
            logService = (ILogService) factory.create(ILogService.class, "http://172.21.154.32:9090/logservice");
            System.out.println("client source init");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("client source init failed");
            System.out.println("fail message：" + ex.getMessage());
        }
    }

}
