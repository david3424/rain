package org.david.rain.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Struts2 Utils类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * 
 * 
 */
public class JsonUtil {
	/**
	 * Logger for this class
	 */
	private Log logger = LogFactory.getLog(JsonUtil.class);

	// header 常量定义
	private  final String ENCODING_PREFIX = "encoding";
	private  final String NOCACHE_PREFIX = "no-cache";
	private  final String ENCODING_DEFAULT = "UTF-8";
	private  final boolean NOCACHE_DEFAULT = true;

	private HttpServletResponse response;
	private String callback;
	
	public JsonUtil(){

	}
	
	public JsonUtil(HttpServletResponse response){
		this.setResponse(response);
	}
	
	public JsonUtil(HttpServletResponse response,String callback){
		this.setResponse(response);
		this.setCallback(callback);
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public void render(final String contentType, final String content,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName,
						NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName
							+ "不是一个合法的header类型");
			}

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}
			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出 json 字符串
	 * 
	 * @param jsonStr
	 */
	public void printToJson(String jsonStr) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();

			out.println(jsonStr);

			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("Error: method Struts2Utils.printToJson() 发生错误");
		}
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param string
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public void renderJson(final String string, final String... headers) {
		render("application/json", string, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public void renderJson(final Object object, final String... headers) {
		String jsonString = null;
		if (object instanceof Collection) {
			if(callback==null){
				jsonString = JSONArray.fromObject(object).toString();
			}else{
				jsonString = callback+"("+JSONArray.fromObject(object).toString()+")";
			}
		} else {
			if(callback==null){
				jsonString = JSONObject.fromObject(object).toString();
			}else{
				jsonString = callback+"("+JSONObject.fromObject(object).toString()+")";
			}		
		}
		renderJson(jsonString, headers);
	}
	
	/**
	 * 直接输出JSON.
	 * 
	 * @param string
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public void renderHtml(final String string, final String... headers) {
		render("text/html;charset=utf-8", string, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public void renderHtml(final Object object, final String... headers) {
		String jsonString = null;
		if (object instanceof Collection) {
			if(callback==null){
				jsonString = JSONArray.fromObject(object).toString();
			}else{
				jsonString = callback+"("+JSONArray.fromObject(object).toString()+")";
			}
		} else {
			if(callback==null){
				jsonString = JSONObject.fromObject(object).toString();
			}else{
				jsonString = callback+"("+JSONObject.fromObject(object).toString()+")";
			}		
		}
		renderHtml(jsonString, headers);
	}
}
