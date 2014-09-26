package org.david.rain.utils;

import oracle.sql.CLOB;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.david.rain.utils.vo.DataElements;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Utility
 * <p>
 * base class, including some base method that can improve our efficiency.
 * 
 * @author pazzini
 *
 */
public class PoiUtils {

	/**
	 * noNullTrim
	 * <p>
	 * trim the object
	 * 
	 * @param obj
	 * @return
	 */
	public static String noNullTrim(Object obj) {
		if(null == obj) {
			return "";
		} else {
			return String.valueOf(obj).trim();
		}
	}
	
	/**
	 * formatDateStr
	 * <p>
	 * format the numeric object
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date formatDateStr(String strDate) {
		return new Date(Long.valueOf(strDate));
	}
	
	/**
	 * noNullTrimDefault
	 * <p>
	 * trim the object with the default value
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String noNullTrimDefault(Object obj, String defaultValue) {
		if(null == obj) {
			return defaultValue;
		} else {
			if("".equals(noNullTrim(obj))) {
				return defaultValue;
			} else {
				return noNullTrim(obj);
			}
		}
	}
	
	//Excel
	/**
	 * getTitleStyle
	 * <p>
	 * get the style of title cell
	 * 
	 * @param wb
	 * 				HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {
		//create cell style
		HSSFCellStyle style = wb.createCellStyle();

		//set the style of cell
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		
		return style;
	}
	
	/**
	 * getMergedRegionStyle
	 * <p>
	 * get the style of region cell that should be merged
	 * 
	 * @param wb
	 * 				HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getMergedRegionStyle(HSSFWorkbook wb) {
		//create cell style
		HSSFCellStyle style = wb.createCellStyle();

		//set the style of cell
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		
		return style;
	}
	
	/**
	 * getStringStyle
	 * <p>
	 * get the style of string cell
	 * 
	 * @param wb
	 * 				HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getStringStyle(HSSFWorkbook wb) {
		//create cell style
		HSSFCellStyle style = wb.createCellStyle();

		//set the style of cell
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		
		return style;
	}
	
	/**
	 * getTimeStyle
	 * <p>
	 * get the style of time cell
	 * 
	 * @param wb
	 * 				HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getTimeStyle(HSSFWorkbook wb) {
		//create cell style
		HSSFCellStyle style = wb.createCellStyle();

		//set the style of cell
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		
		return style;
	}
	
	/**
	 * getNumberStyle
	 * <p>
	 * get the style of number cell
	 * 
	 * @param wb
	 * 				HSSFWorkbook
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getNumberStyle(HSSFWorkbook wb) {
		//create cell style
		HSSFCellStyle style = wb.createCellStyle();

		//set the style of cell
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setLocked(true);
//		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
		
		return style;
	}
	
	public static String formatNumber(Object aimValue) {
		if(null == aimValue) {
			return "";
		}
		NumberFormat format = new DecimalFormat("###,###,###,###,###.##");
		return format.format(aimValue);
	}
	
	public static String formatInteger(Object aimValue) {
		if(null == aimValue) {
			return "";
		}
		NumberFormat format = new DecimalFormat("###,###,###,###,###");
		return format.format(aimValue);
	}
	
	public static String formatPostiveInteger(Object aimValue) {
		if(null == aimValue) {
			return "";
		}
		BigDecimal temp = new BigDecimal(String.valueOf(aimValue));
		NumberFormat format = new DecimalFormat("###,###,###,###,###");
		return format.format(temp.abs());
	}
	
	/**
	 * uploadFile
	 * <p>
	 * upload file
	 * 
	 * @param file
	 * @param filePath
	 * @throws java.io.IOException
	 */
	public static void uploadFile(MultipartFile file, String filePath) throws IOException {
		//get the input stream of file
		InputStream in = file.getInputStream();
		//define the output stream for file
		FileOutputStream fos = null;
		
		try {
			//the stream of file on server
			fos = new FileOutputStream(filePath);
			
			//copy data
			byte[] b = new byte[1024];
			int length = -1;
			while(-1 != (length = in.read(b))) {
				fos.write(b, 0, length);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
	
	/**
	 * downloadFile
	 * <p>
	 * download file
	 * 
	 * @param response
	 * @param filePath
	 * @param downloadFileName
	 */
	public static void downloadFile(HttpServletResponse response, String filePath, String downloadFileName) {
		//download setting
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-disposition", "attachment; filename=" + downloadFileName);
		
		//initialize the outputStream
		OutputStream out = null;
		FileInputStream fis = null;
		try {
			byte[] b = new byte[1024];
			fis = new FileInputStream(filePath);
			//get the outputStream from response
			out = response.getOutputStream();
			int len;
			//clone the file from server
			while(-1 != (len = fis.read(b))) {
				out.write(b, 0, len);
			}
		} catch (IOException e) {
			//error, print the error
			e.printStackTrace();
		} finally {
			//close the outputStream
			IOUtils.closeQuietly(out);
			//close the inputStream
			IOUtils.closeQuietly(fis);
		}
	}
	
	public static String clobContent(CLOB obj) {
		StringBuilder temp = new StringBuilder("");
		BufferedReader lnr = null;
		try {
			lnr = new BufferedReader(obj.getCharacterStream());
			
			String lineContent = "";
			
			while(null != (lineContent = lnr.readLine())) {
				temp.append(lineContent + " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(lnr);
		}

		return temp.toString();
	}
	
	public static DataElements createDataElements(String name, String gbkName, DataElements.Type type, int colWidth) {
		DataElements dataElements = new DataElements();
		dataElements.setName(name);
		dataElements.setGbkName(gbkName);
		dataElements.setType(type);
		dataElements.setExcelColWidth(colWidth);
		return dataElements;
	}
}
