package org.david.rain.utils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;


public class StrUtils {

	public static String beanToString(Object object) {
		return ToStringBuilder.reflectionToString(object,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static String dealOsType(Object type) {
		if (type == null) {
			return "";
		} else if (((Integer) type).intValue() == 1) {
			return "iOS";
		} else if (((Integer) type).intValue() == 2) {
			return "Android";
		} else {
			return "";
		}
	}

	public static String dealNullStr(Object object) {
		if (object == null || object.toString().equals("")) {
			return "0";
		}
		return object.toString();
	}

	public static String dealNullStrObject(Object object) {
		if (object == null || object.toString().equals("")) {
			return "";
		}
		return object.toString();
	}

	public static Integer dealIntObject(Object object) {
		if (object == null) {
			return new BigDecimal(0).intValue();
		}
		return ((BigDecimal) object).intValue();
	}

	public static double dealDoubleFormat(Object object) {
		if (object == null) {
			return 0;
		}
		return ((BigDecimal) object).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	public static long dealLongFormat(Object object) {
		if (object == null) {
			return 0;
		}
		return ((BigDecimal) object).longValue();
	}

	public static String dealHourFormat(String second) {
		String result = "";
		int time = Integer.parseInt(second);
		int hour = time / 3600;
		if (hour != 0) {
			result = getTwoFormat(hour) + ":";
		} else {
			result = "00:";
		}
		time = time % 3600;
		int minite = time / 60;
		if (minite != 0) {
			result = result + getTwoFormat(minite) + ":";
		} else {
			result = result + "00:";
		}
		time = time % 60;
		result = result + getTwoFormat(time);
		return result;
	}

	public static String getTwoFormat(int time) {
		String timeStr = String.valueOf(time);
		if (timeStr.length() == 1) {
			return "0" + timeStr;
		} else {
			return timeStr;
		}
	}

	/**
	 * 
	 * @Title: formatDouble
	 * @Description: 格式化百分比信息
	 * @param i
	 *            除数
	 * @param j
	 *            被除数
	 * @return
	 * @throws
	 */
	public static String formatDouble(Integer i, Integer j) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		return df.format((double) i * 100 / j) + "%";
	}

	/**
	 * 
	 * @Title: getTopicDomain
	 * @Description:获取专题域名
	 * @param url
	 * @return
	 * @throws
	 */
	public static String getTopicDomain(String url) {
		int startposition = url.indexOf("://");
		startposition = (startposition < 0) ? 0 : startposition + 3;
		int endposition = url.indexOf("/", 10);
		endposition = (endposition < 0) ? url.length() : endposition;
		url = url.substring(startposition, endposition);
		if (url.startsWith("pppppp")) {
			url = url.replaceFirst("pppppp", "www");
		}
		return url;
	}

	/**
	 * 
	 * @Title: getTopicPath
	 * @Description: 获取专题路径
	 * @param url
	 * @return
	 * @throws
	 */
	public static String getTopicPath(String url) {
		int startposition = url.indexOf("://");
		startposition = (startposition < 0) ? 0 : startposition + 3;
		url = url.substring(startposition);
		int position = url.indexOf("/");
		if (position < 0) {
			return "/";
		} else {
			String str = url.substring(position);
			int p = str.indexOf(".", str.length() - 8);
			if (p < 0 && !str.endsWith("/")) {
				str = str + "/";
			}
			return str;
		}
	}

	/**
	 * 
	 * @Title: getRealUrlForHref
	 * @Description: 替换带eventXX的错误URL
	 * @param url
	 *            指定URL
	 * @return
	 * @throws
	 */
	public static String getRealUrlForHref(String url) {
		if (url.contains("event51") || url.contains("event50")
				|| url.contains("event21") || url.contains("event20")) {
			int startposition = url.indexOf("://");
			startposition = (startposition < 0) ? 0 : startposition + 3;
			url = url.substring(startposition);
			int startPoint = url.indexOf(".");
			String topicName = url.substring(0, startPoint);
			int startPosition = url.indexOf("/");
			int endPosition = url.indexOf("/", startPosition + 1);
			String event = url.substring(startPosition + 1, endPosition);
			if (event.equals("pppppp")) {
				event = "www";
				url = url.substring(0, startPosition + 1) + event
						+ url.substring(endPosition);
			}
			url = url.replaceFirst(event, topicName);
			url = "http://" + url.replaceFirst(topicName, event);
		} else if (!url.contains("http://")) {
			return "http://" + url;
		}
		return url;
	}

	public static String matchesString(String s) {
		String buffer = s;
		if (s != null && !"".equals(s.trim())) {
			String regex = ".*?[\\u4e00-\\u9fa5].*?";
			boolean flag = s.matches(regex);
			if (!flag) {
				// 首先使用转码的方式测试
				try {
					String str = java.net.URLDecoder.decode(s, "UTF-8");
					flag = str.matches(regex);
					if (flag) {
						return str;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			int i = 0;
			// 处理浏览器发送的请求
			while (!flag && i < charsetArray.length) {
				try {
					String newStr = new String(s.getBytes("ISO8859-1"),
							charsetArray[i]);
					if (i == 0) {
						// 缓存第一次的请求
						buffer = newStr;
					}
					i++;
					flag = newStr.matches(regex);
					if (flag) {
						return newStr;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return buffer;
	}

	private static String[] charsetArray = new String[] { "UTF-8", "GBK",
			"GB2312" };

	public static String md5(String source) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes("UTF-8"));
			byte bytes[] = md.digest();
			String tempStr = "";
			for (int i = 0; i < bytes.length; i++) {
				tempStr = (Integer.toHexString(bytes[i] & 0xff));
				if (tempStr.length() == 1) {
					sb.append("0").append(tempStr);
				} else {
					sb.append(tempStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(StrUtils.sql_inj("truncate sys_log"));
	}

	public static boolean sql_inj(String str) {
		String inj_str = "insert|delete|update|truncate|declare|alter|drop|grant|revoke";
		// 这里的东西还可以自己添加
		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
}
