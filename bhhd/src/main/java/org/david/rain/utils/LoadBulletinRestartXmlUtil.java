package org.david.rain.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 在tomcat重新启动之时，通过读取配置文件的配置信息，决定哪些游戏的公告需要重新启动，并发布
 * 
 * bulletinrestat.properties主要记录key所代表的游戏下是否重启公告
 * 
 * @author liuxiaochong
 * 
 */
public class LoadBulletinRestartXmlUtil {

	private static Properties prop = null;

	private static final String BULLETIN_XML_PATH = "/config/bulletinrestart.properties";

	private LoadBulletinRestartXmlUtil() {
	}

	static {
		InputStream in = null;
		try {
			in = LoadBulletinRestartXmlUtil.class.getResourceAsStream(BULLETIN_XML_PATH);
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
