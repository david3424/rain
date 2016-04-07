package org.david.rain.wmproxy.module.constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SystemConstant {

	// csv application/vnd.ms-excel
	// xlsx
	// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
	// xls application/vnd.ms-excel
	// txt text/plain
	
	public static String ALLOW_UPLOAD_FILE_CONTENT_TYPES = "application/vnd.ms-excel|application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" +
			"|application/vnd.ms-excel|text/plain";
	
	
	public static void main(String[] args){
		
		Pattern p = Pattern.compile(SystemConstant.ALLOW_UPLOAD_FILE_CONTENT_TYPES, 
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher("application/vnd.ms-excel");
		System.out.println(m.find());
	}
}
