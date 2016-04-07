package org.david.rain.webservice;

import com.caucho.hessian.client.HessianProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class ServiceManage {

    protected static Logger log = LoggerFactory.getLogger(ServiceManage.class);

    public static ServiceInterface serviceInterface = null;


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
            String propsfilename = "/wsurl.properties";
            InputStream is = ServiceManage.class.getResourceAsStream(propsfilename);
            Properties p = new Properties();
            p.load(is);
            HessianProxyFactory factory = new HessianProxyFactory();
            serviceInterface = (ServiceInterface) factory.create(ServiceInterface.class, p.getProperty("activityurl"));

            log.info("接口初始化成功。");
        } catch (Exception ex) {

            log.error("接口初始化连接失败", ex);
        }
    }
}
