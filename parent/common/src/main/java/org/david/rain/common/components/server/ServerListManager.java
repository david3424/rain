package org.david.rain.common.components.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: david
 * Date: 12-10-29
 * Time: 上午9:27
 */
@Component
@PropertySource("classpath:/common.properties")
public class ServerListManager implements InitializingBean {


    static Logger logger = LoggerFactory.getLogger(ServerListManager.class);

    @Autowired
    private Environment environment;


    @Override
    public void afterPropertiesSet() throws Exception {
        String xmlPath = environment.getProperty("serverlist.xmlpath");
        if (xmlPath == null || xmlPath.isEmpty()) {
            this.serverXmlPath = "http://public.david.com/serverlist.xml";
        } else {
            this.serverXmlPath = xmlPath;
        }
    }

    private String serverXmlPath;
    private String serverListXML;

    private Map<String, List<Map<String, Object>>> serverList = new HashMap<>();

    private final static String[] OUT_PUT_PARAM_ARRAY = {"aid", "id", "name"};
    //更新操作
    private void updateServerList() {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            logger.debug("start to connect switch");
            URL url = new URL(serverXmlPath);
            is = url.openStream();
            logger.debug("end connect and read");
            reader = new BufferedReader(new InputStreamReader(is, "gbk"));
            StringBuilder sb = new StringBuilder(1000);
            while (true) {
                String str = reader.readLine();
                if (str != null) {
                    sb.append(str).append("\n");
                } else {
                    break;
                }
            }
            serverListXML = sb.toString();
            logger.debug("read xml end and begin xml to json");
            this.serverList = xml2Map(serverListXML);
            logger.debug("xml to json end");
        } catch (DocumentException |  IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Map<String, List<Map<String, Object>>> xml2Map(String xml) throws IOException,  DocumentException {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("gbk")));
        Element root = doc.getRootElement();
        result.put(root.getName(), iterateZones(root));
        return result;
    }

    private List<Map<String, Object>> iterateZones(Element root)  {
        List<Element> zones = root.elements("zone");
        List<Map<String, Object>> result = new ArrayList<>();
        for (Element zone : zones) {
            if ("true".equals(zone.attributeValue("hidden"))) {
                continue;
            }
            Map<String, Object> object = new HashMap<>();
            for (String param : OUT_PUT_PARAM_ARRAY) {
                object.put(param, zone.attributeValue(param));
            }
            result.add(object);
        }
        return result;
    }


    public String getServerListXML() {
        if ("".equals(serverListXML)) {
            synchronized (this) {
                if ("".equals(serverListXML))
                    updateServerList();
            }
        }
        return serverListXML;
    }

    public Map<String, List<Map<String, Object>>> getServerListMap() {
        if (serverList.size() == 0) {
            synchronized (this) {
                logger.debug("enter update list lock thread:"+Thread.currentThread().getId());
                if (serverList.size() == 0)
                    updateServerList();
                logger.debug("update list done thread:"+Thread.currentThread().getId());
            }
            logger.debug("out of list lock thread:" + Thread.currentThread().getId());
        }
        return serverList;
    }

    public String getServerXmlPath() {
        return serverXmlPath;
    }

    public void setServerXmlPath(String serverXmlPath) {
        this.serverXmlPath = serverXmlPath;
    }
}
