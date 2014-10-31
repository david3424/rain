package org.david.rain.utils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes" })
public class XmlUtil {
    private static Logger logger  = LoggerFactory.getLogger(XmlUtil.class);
//	 private static String configPath = ConfigUtil.defaultConfigPath()
//	 + "/serverXml/"; //serverlist/

	public XmlUtil() {
	}

	//private final static String GAMEXMLPATH = configPath + "server.xml"; //原路径

 	//private final static String GAMEXMLPATH = "/export/webapps/webgame/serverXml/serverlist/hzxf.xml";
	

	private static Document doc;

//	static {
//		doc = parseDOMTree();
//	}
	
	//系统公告重启时使用，分商品
	public static void getDomByGame(Integer game_id){
		String xmluri = LoadServerXmlUtil.getValue(String.valueOf(game_id));
		System.out.println("-------!!!!!!!!!!!!!===xmluri==" + xmluri); //这里只要获取正确就可以
		doc = parseDOMTree(xmluri);
	}
	
	public static void getDom(){
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(null != sra){
			Integer currentGameId = Utils.getCurrentGameId();
			if(!"-1".equals(currentGameId)){ //可以通过SESSION获取商品
				String xmluri = LoadServerXmlUtil.getValue(String.valueOf(currentGameId));
				System.out.println("`````````!!!!!!!!!!!!!===xmluri==" + xmluri); //这里只要获取正确就可以
				doc = parseDOMTree(xmluri);
                logger.debug("{}",doc);
			}
		}
		
		
	}
	

	public static Document parseDOMTree(String xmluri) {
		Document document = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			document = builder.build(new File(xmluri));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}


	private static Map<String, String> getIdNameMap() {
		getDom();
		Element rootElement = doc.getRootElement();
		List list = rootElement.getChildren("zone");
		Map<String, String> map = null;
		if (list != null) {
			map = new HashMap<String, String>(list.size());
			for (int i = 0; i < list.size(); i++) {
				Element n = (Element) list.get(i);
				map.put(n.getAttributeValue("serverid"), n.getAttributeValue("name"));
			}
		} 
		return map;
	}

	/**
	 * 将列表的行列互换
	 * 
	 * @param t
	 * @return
	 */
	private static String[][] reverseTable(String[][] t) {
		if (null != t && 0 != t.length && null != t[0]) {
			int col = t.length;
			int row = t[0].length;
			String[][] re = new String[row][col];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					try {
						re[i][j] = t[j][i];
					} catch (Exception e) {
						re[i][j] = "";
					}
				}
			}
			return re;
		}
		return null;
	}

}
