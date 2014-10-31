package org.david.rain.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载获取连接客服接口的配置文件server.xml
 * 
 * serverxml.properties主要记录商品对应的xml地址
 * 
 * @author GAMEUSER
 * 
 */
public class LoadServerXmlUtil {

	private static Properties prop = null;

	private static final String SERVER_XML_PATH = "/config/serverxml.properties";

	private LoadServerXmlUtil() {
	}

	static {
		InputStream in = null;
		try {
			in = LoadServerXmlUtil.class.getResourceAsStream(SERVER_XML_PATH);
			prop = new Properties();
			prop.load(in);
			if (in != null)
				in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		try {
			return prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return key;
		}
	}

}
