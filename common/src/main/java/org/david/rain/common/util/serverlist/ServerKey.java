package org.david.rain.common.util.serverlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 11-12-28
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public class ServerKey implements Comparable<ServerKey>,Serializable {
    private final static List<String> nums = new ArrayList<String>(30);
	static {
		nums.add("零");
		nums.add("一");
		nums.add("二");
		nums.add("三");
		nums.add("四");
		nums.add("五");
		nums.add("六");
		nums.add("七");
		nums.add("八");
		nums.add("九");
		nums.add("十");
		nums.add("十一");
		nums.add("十二");
		nums.add("十三");
		nums.add("十四");
		nums.add("十五");
		nums.add("十六");
		nums.add("十七");
		nums.add("十八");
		nums.add("十九");
		nums.add("二十");
		nums.add("二十一");
		nums.add("二十二");
		nums.add("二十三");
		nums.add("二十四");
		nums.add("二十五");
		nums.add("二十六");
		nums.add("二十七");
		nums.add("二十八");
		nums.add("二十九");

	}
	private String serverName;

	public ServerKey() {
	};

	public ServerKey(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int compareTo(ServerKey o) {
		String key1 = this.serverName;
		String key2 = o.serverName;
		StringBuffer sb = null;
		for (int i = 0; i < nums.size(); i++) {
			sb = new StringBuffer(i + "");
			if (sb.length() == 1) {
				sb.insert(0, "0");
			}
			key1 = getConvertKey(key1, nums.get(i), sb.toString());
			key2 = getConvertKey(key2, nums.get(i), sb.toString());
		}
		return key1.compareTo(key2);
	}

	private static String getConvertKey(String key, String oldStr, String newStr) {
		if (key.contains(oldStr)) {
			return key.replace(oldStr, newStr);
		} else {
			return key;
		}
	}

	@Override
	public String toString() {
		return serverName;
	}
}
