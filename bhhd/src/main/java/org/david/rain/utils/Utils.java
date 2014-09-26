package org.david.rain.utils;

import org.david.rain.security.CustomUserDetails;
import org.david.rain.security.tool.SessionUser;
import org.david.rain.model.GameBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialClob;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public final class Utils {

	private Utils() {
	}

	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final Calendar calendar = Calendar.getInstance();
	public static final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	public static final Map<Integer, String> signMap = new HashMap<Integer, String>();

	/**
	 * 把一个double转换为0.0%的字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String double2Persent(Double d) {
		NumberFormat format = new DecimalFormat("0.0'%'");
		return format.format(d);
	}

	public static String formatGameId(Integer gameId) {
		String str = String.valueOf(gameId);
		int length = str.length();
		String gameStr = str;
		switch (length) {
		case 1:
			gameStr = "000" + str;
			break;
		case 2:
			gameStr = "00" + str;
			break;
		case 3:
			gameStr = "0" + str;
			break;
		default:
			break;
		}
		return gameStr;
	}

	public static String formatGameId(String gameId) {
		int length = gameId.length();
		switch (length) {
		case 1:
			return "000" + gameId;
		case 2:
			return "00" + gameId;
		case 3:
			return "0" + gameId;
		default:
			return gameId;
		}
	}

	/**************************************** 下面的工具类用于从ORACLE取出数据后 ********************************************/
	public static Integer formatBigDeceimalToInt(Object o) {
		BigDecimal num = (BigDecimal) o;
		if (null != num) {
			return num.intValue();
		}
		return 0;
	}

	public static Long formatBigDeceimalToLong(Object o) {
		BigDecimal num = (BigDecimal) o;
		if (null != num) {
			return num.longValue();
		}
		return 0l;
	}

	public static Double formatBigDeceimalToDouble(Object o) {
		BigDecimal num = (BigDecimal) o;
		if (null != num) {
			return num.doubleValue();
		}
		return 0d;
	}
	
	public static Double formatBigDeceimalToDouble(Object o, int n){
		BigDecimal num = (BigDecimal) o;
		if(null != num){
			return num.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return 0d;
	}

	/**
	 * 将时间转换为字符串格式
	 * 
	 * @param date
	 *            时间
	 * @return 具体格式日期
	 */
	public static String converDateTimestampToStr(Date date) {
		if (date == null) {
			return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).format(new Date(
					System.currentTimeMillis()));
		}
		return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).format(date);
	}

	/**
	 * 获得当前登陆用户帐号
	 */
	public static String getLoginAccount() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication auth = securityContext.getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof UserDetails) {
				return ((CustomUserDetails) principal).getAccount();
			} else {
				return principal.toString();
			}
		}

		return "";
	}

	/**
	 * 获得当前登陆用户信息
	 */
	public static CustomUserDetails getLoginUserDetail() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication auth = securityContext.getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof UserDetails) {
				return ((CustomUserDetails) principal);
			} else {
				return null;
			}
		}

		return null;
	}

	/**
	 * 
	 * @Title: getIpAddr
	 * @Description: 获取IP
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

//		String ip1 = request.getHeader("x-forwarded-for");
//		if (ip1 == null || ip1.length() == 0 || "unknown".equalsIgnoreCase(ip1)) {
//			ip1 = "";
//		}
//
//		String ip2 = request.getHeader("Proxy-Client-IP");
//		if (ip2 == null || ip2.length() == 0 || "unknown".equalsIgnoreCase(ip2)) {
//			ip2 = "";
//		}
//
//		String ip3 = request.getHeader("WL-Proxy-Client-IP");
//		if (ip3 == null || ip3.length() == 0 || "unknown".equalsIgnoreCase(ip3)) {
//			ip3 = "";
//		}
//
//		String ip4 = request.getRemoteAddr();
//		if (ip4 == null || ip4.length() == 0 || "unknown".equalsIgnoreCase(ip4)) {
//			ip4 = "";
//		}
//
//		return ip1 + " " + ip2 + " " + ip3 + " " + ip4;
	}

	/**
	 * 将字符串转换成集合
	 * 
	 * @param strings
	 *            以逗号分割的字符串
	 * @return 具体日期
	 */
	public static List<Integer> stringsToIntegerList(String strings) {
		List<Integer> integerList = new ArrayList<Integer>();
		if (strings == null)
			return integerList;
		if (strings.indexOf(",") == -1) {
			integerList.add(Integer.parseInt(strings));
		} else {
			String[] stringList = strings.split(",");
			for (int i = 0; i < stringList.length; i++) {
				integerList.add(Integer.parseInt(stringList[i]));
			}
		}

		return integerList;
	}

	/**
	 * 
	 * @Title: getMd5
	 * @Description: md5加密
	 * @param pwd
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public static String getMd5(String password, String account) {
		String md5Pwd = passwordEncoder.encodePassword(password, account);

		// try {
		// BASE64Encoder base = new BASE64Encoder();
		// MessageDigest msg = MessageDigest.getInstance("MD5");
		// md5Pwd = base.encode(msg.digest(pwd.getBytes("utf-8")));
		// } catch (NoSuchAlgorithmException e) {
		// e.printStackTrace();
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return md5Pwd;
	}

	public static void returnResultForHession(String s) throws Exception {
		JSONObject json = JSONObject.fromObject(s);
		if (null == json || "".equals(json))
			throw new Exception("hession调用无返回");
		String code = json.getString("code");
		if (!code.equals("0")) {
			throw new Exception(StrUtils.matchesString(json.getString("error_msg")));
		}
	}
	
	/**
	 * 判断调用游戏的HTTP接口是否成功返回
	 * @param jo
	 * @return
	 * @author yuhongyong
	 * @date Jan 24, 2013
	 */
	public static boolean returnSuccess4GameTool(JSONObject jo)
	{
		if (null==jo)
			return false;
		String code = jo.getString("code");
		return code.equals("0");
	}
	/**
	 * 运营工具向GameServer发送HTTP请求后返回的状态码生成相应的消息<br/>
	 * 0：成功返回；其他未知
	 * @param jo
	 * @return
	 * @author yuhongyong
	 * @date Jan 7, 2013
	 */
	public static String returnMsg4GameTool(JSONObject jo)
	{
		if (null==jo)
		{
			return "游戏服返回Null";
		}
		String msg = "";
		String code = jo.getString("code");
		if (code.equals("0"))
		{
			msg = "成功";
		}
		else if (StringUtils.isNotBlank(jo.getString("error_msg")))
		{
			msg = jo.getString("error_msg");
		}
		else
		{
			JSONArray jArray = JSONArray.fromObject(jo);
			msg = jArray.toArray().toString();
		}
		return msg;
	}
	/**
	 * 通过HTTP请求获取json格式的返回
	 * @param urlStr
	 * @param params
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject httpGetJSON(String urlStr, Map params)throws UnsupportedEncodingException,IOException
	{
		String result = http(urlStr, params, "GET");
		return JSONObject.fromObject(result);
	}

	/**
	 * 通过HTTP请求获取json格式的返回
	 * @param urlStr
	 * @param params
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject httpPostJSON(String urlStr, Map params) throws UnsupportedEncodingException, IOException
	{
		String result = http(urlStr, params, "POST");
		return null ;//JSONObject.fromObject(result);//.parseObject(result);
	}
	/**
	 * GET or POST HTTP access
	 * @param url
	 * @param params
	 * @param httpMethod GET,POST
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 * @author yuhongyong
	 * @date Apr 9, 2013
	 */
	public static String http(String url,Map<String, String> params,String httpMethod)throws UnsupportedEncodingException,IOException
	{
		// validate argument
		if (StringUtils.isBlank(url))
			throw new IllegalArgumentException("url is null");
		if (StringUtils.isBlank(httpMethod))
			throw new IllegalArgumentException("HTTP method is null");
		if (!httpMethod.equalsIgnoreCase("get")&&!httpMethod.equalsIgnoreCase("post"))
			throw new IllegalArgumentException("HTTP method must be get or post");
		// log URL and params in form of :http://xxx?p1=123&p2=456
		StringBuilder urlGET = new StringBuilder(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();// this for HTTP post
		if (null!=params&&!params.isEmpty())
		{
			urlGET.append("?");
			for (Entry<String,String> e : (Set<Entry<String,String>>) params.entrySet())
			{
				nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
				// join whole URL
				urlGET.append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), HTTP.UTF_8)).append("&");
			}
			urlGET.deleteCharAt(urlGET.length()-1);// remove the last &
		}
		if (httpMethod.equalsIgnoreCase("get"))
			System.out.println("[GET] "+urlGET);
		else 
			System.out.println("[POST] "+urlGET);
		
		// set timeout
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 30000);// get HTTP connection timeout
		httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, 100000);// read response data timeout
		// HTTP access
		HttpClient httpclient = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		if (httpMethod.equalsIgnoreCase("get"))
		{
			HttpGet httpGet = new HttpGet(urlGET.toString());
			response = httpclient.execute(httpGet);
		}
		else // defaul is post
		{
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httppost);
		}
		if (null == response) return null;
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity == null) return null;
		String feedback = EntityUtils.toString(httpEntity);
		System.out.println("HTTP feedback:"+feedback);
		return feedback;
	}
	/**
	 * make json-string for HTTP access
	 * @param failed
	 * @param detailMsg
	 * @return
	 * @author yuhongyong
	 * @date May 12, 2013
	 */
	public static JSONObject makefeedback4HTTP(boolean failed,String detailMsg)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		if (failed)
		{
			map.put("code", "1");
			map.put("error_msg", detailMsg);
		}
		else
		{
			map.put("code", "0");
		}
		return JSONObject.fromObject(map);
	}
	
	/**
	 * 将String转成Clob对象，失败返回null
	 * @param str
	 * @return
	 */
	public static Clob stringToClob(String str) {
		if (str == null) {
			return null;
		}
		Clob c = null;
		try {
			c = new SerialClob(str.toCharArray());
		} catch (Exception e) {
		} 
		return c;
	}
	
	/**
	 * 将Clob对象转换为String，失败返回null
	 * @param c
	 * @return
	 */
	public static String clobToString(Clob c) {
		if (c == null) {
			return null;
		}
		StringBuilder s = new StringBuilder();
		Reader cStream = null;
		try {
			cStream = c.getCharacterStream();
			char[] b = new char[1024];
			int len = 0;
			while ((len = cStream.read(b)) != -1) {
				s.append(b, 0, len);
			}
		} catch (Exception e) {
			s = null;
		} finally {
			IOUtils.closeQuietly(cStream);
		}
		if (s == null) {
			return null;
		}
		return s.toString();
	}

	public static synchronized String getSign(int key) {
		if (signMap.isEmpty()) {
			signMap.put(1, "=");
			signMap.put(2, ">");
			signMap.put(3, ">=");
			signMap.put(4, "<");
			signMap.put(5, "<=");
		}

		return signMap.get(key);
	}
	
	/**
	 * 模拟PHP的htmlSpecialChars,替换HTML标记
	 * @param String
	 * @return 
	 */
	public static  String htmlSpecialChars(String str){
		str =str.replaceAll("&", "&amp;");
		str =str.replaceAll("<", "&lt;");
		str =str.replaceAll(">", "&gt;");
		str =str.replaceAll("\"", "&quot;");
		str =str.replaceAll("'", "&#x27;");
		return str;
	}

	/**
	 * 取得当前用户访问的游戏ID
	 */
	public static Integer getCurrentGameId() {
		Integer gameId = -1;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		String currentGameShort = (String)session.getAttribute(SessionUser.SESSION_CURRENT_GAME_SHORT);
		SessionUser sessionUser = (SessionUser) session.getAttribute(SessionUser.SESSION_ROOT_KEY);
		Map<String, GameBean> gameMap = sessionUser.getGameMap();
		GameBean gameBean = gameMap.get(currentGameShort);
		if (gameBean != null) {
			gameId = gameBean.getGameId();
		}
		return gameId;
	}

}
