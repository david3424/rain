/*
*  时间： 2007-7-24 11:39:09
*/
package org.david.rain.wmproxy.module.util;

import javax.servlet.http.HttpServlet;

import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

/**
 */

public class ConfigUtil {

        public ConfigUtil() {

    }

    /**
     * 路径分隔符
     */
    public static final String FILESEPARATOR = System.getProperty("file.separator");

    /**
     * 根据本类所在的位置，获得Config文件的目录的路径。因为类缺省放在/WEB-INF/classes或/WEB-INF/lib下
     * 而config文件则缺省是放在/WEB-INF/下
     *
     * @return Config文件的路径。如果没有找到，return null;
     */
    public static String defaultConfigPath() {
        return defaultConfigPath(ConfigUtil.class);
        
    }

    /**
     * 获得缺省的安装目录
     *
     * @return 缺省的安装目录
     */
    public static String defaultHomePath() {
        String configPath = defaultConfigPath();
        if (configPath == null) {
            return null;
        }
        int n = configPath.lastIndexOf(FILESEPARATOR);
        if (n > 0) {
            return configPath.substring(0, n);
        }
        return configPath;
    }

    /**
     * 获得指定文件名的绝对路径（包含指定文件名）。defaultConfPath() + 分隔符 + confFileName
     *
     * @param confFileName
     *            指定Config文件的文件名
     * @return 配置文件的绝对路径,如果没有找到配置文件目录，return null;
     */
    public static String getDefaultConfFile(String confFileName) {
        String path = defaultConfigPath();
        if (path == null) {
            return null;
        }
        String s = path + FILESEPARATOR + confFileName;
        return s;

    }

    /**
     * 根据指定类所在的位置，获得Config文件的目录路径。因为类缺省放在/WEB-INF/classes或/WEB-INF/lib下
     * 而config文件则缺省是放在/WEB-INF/下
     *
     * @param c
     *            指定类
     * @return 根据指定类所在的位置,Config文件的路径。如果没有找到，return null;
     */
    @SuppressWarnings("unchecked")
	public static String defaultConfigPath(Class c) {
        String startMark = "file:";
        String endMark = "/WEB-INF";
        String s = c.getName().replace('.', '/') + ".class";
        // 当资源class放在/WEB-INF/classs中时，
        // url=file:/e:/struts2demo/project/defaultroot/WEB-INF/classes/com.world2.util.ConfigUtil.class
        // 当资源class打成jar包放在/WEB-INF/lib中时，
        // url=jar:file:/e:/struts2demo/project/defaultroot/WEB-INF/lib/res.jar!/com.world2.util.ConfigUtil.class
        java.net.URL url = c.getClassLoader().getResource(s);
        String upath = url.toString();

        int n1 = upath.indexOf(startMark);
        n1 = n1 < 0 ? 0 : n1 + startMark.length();
        int n2 = upath.lastIndexOf(endMark + "/classes");
        if (n2 < 0) {
            n2 = upath.lastIndexOf(endMark + "/lib");
            if (n2 < 0) {
                return null;
            }
        }
        n2 += endMark.length();

        String path = upath.substring(n1, n2);
        if (path.startsWith("/") && path.indexOf(":") == 2) {
            path = path.substring(1);
        }

        return path.replace('/', FILESEPARATOR.charAt(0));
    }

    public static String getConfigPath() {
        String cpath = "";
        cpath = ConfigUtil.class.getResource("/").getPath();
        try {
            cpath = URLDecoder.decode(cpath, "UTF-8");
            int posi = cpath.indexOf("webapps");
            if (posi > 0) {
                cpath = cpath.substring(0, posi);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cpath;
    }

    /**
     *
     * @return Tomcat的绝对路径
     */
    public static String getCATALINAHOME() {
        String cpath = "";
        cpath = ConfigUtil.class.getResource("/").getPath();
        try {
            cpath = URLDecoder.decode(cpath, "UTF-8");
            int posi = cpath.indexOf("webapps");
            if (posi != -1) {
                cpath = cpath.substring(0, posi);
            } else {
                cpath = HttpServlet.class.getResource("/").getPath();
                cpath = cpath.replaceFirst("common/classes/", "");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(System.err);
        }
        return cpath;

    }
}
