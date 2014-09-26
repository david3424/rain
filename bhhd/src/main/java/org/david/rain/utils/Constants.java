package org.david.rain.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

	private static Properties prop = null;

	private Constants() {
	}

	static {
		InputStream in = null;
		try {
			in = Constants.class
					.getResourceAsStream("/config/usermessage.properties");
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
	public static final String DATATABLES_IDISPLAYSTART = "iDisplayStart";
	public static final String DATATABLES_IDISPLAYLENGTH = "iDisplayLength";
	public static final String DATATABLES_MDATAPROP = "mDataProp_";
	public static final String DATATABLES_ISORTCOL = "iSortCol_0";
	public static final String DATATABLES_SSORTDIR = "sSortDir_0";
	public static final String DATATABLES_SSEARCH = "sSearch";
	public static final int IS_ADMIN = 900;
	public static final int STATUS_YES = 200;
	public static final int EXIST_FLAG = 2;
	public static final int COL_TYPE_NUMBER = 1;
	public static final int COL_TYPE_DATE = 2;
	public static final int COL_TYPE_VARCHAR = 3;
	public static final int COL_SEARCH_FLAG = 1;
	public static final int USER_LOGIN_LOG_TYPE = 1;
	public static final int USER_OPTION_LOG_TYPE = 2;
}
