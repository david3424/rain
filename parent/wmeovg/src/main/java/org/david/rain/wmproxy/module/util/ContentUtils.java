package org.david.rain.wmproxy.module.util;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.david.rain.wmproxy.module.constants.SystemConstant;
import org.david.rain.wmproxy.module.upload.entity.ResultBean;
import org.david.rain.wmproxy.module.upload.entity.UploadPrizeBean;

public class ContentUtils {

	public static boolean isContentTypeAllow(String contentType) {

		Pattern p = Pattern.compile(
				SystemConstant.ALLOW_UPLOAD_FILE_CONTENT_TYPES,
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(contentType);

		return m.find();
	}

	public void t() throws Exception{
		
		Set<UploadPrizeBean> beansFromXLSX = getResultBeanFromXLSX("E:\\11.xlsx").getBeans();
		JSONArray fromObject = JSONArray.fromObject(beansFromXLSX);
		
		
		System.out.println(fromObject.toString());
	}
	
	public static ResultBean getResultBeanFromXLSX(String filePath) throws Exception {

		// filePath = "E:\\11.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook(filePath);
		
		ResultBean resultBean = new ResultBean();
		Set<UploadPrizeBean> uploadPrizeBeans = new TreeSet<UploadPrizeBean>();
		
		int appid = 0;
		int zeroCount = 0;
		
		int xmlTotalCount = 0;
		int xmlRepeatCount = 0;
		
		for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
			XSSFSheet sheet = workbook.getSheetAt(k);

			XSSFRow rowTitle = sheet.getRow(0);
			if(null == rowTitle) 
				continue;
			int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
//			if(cellCount != 4)
            if(cellCount > 6||cellCount < 4)
				continue;
			
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				UploadPrizeBean uploadPrizeBean = new UploadPrizeBean();
				XSSFCell cell = row.getCell(0);
				if (null != cell){
					uploadPrizeBean.setAppid(getCell(cell));
					if(StringUtils.length(uploadPrizeBean.getAppid()) <5)
						throw new Exception("应用标识不能小于5个字符：" + uploadPrizeBean.getAppid() );
				}else{
					appid ++;
				}
				cell = row.getCell(1);
				if (null != cell)
					uploadPrizeBean.setPrizename(getCell(cell));
				cell = row.getCell(2);
				if (null != cell) {
					uploadPrizeBean.setPrizeid(Double.valueOf(getCell(cell))
							.intValue());
				}else{
					throw new Exception("物品ID不能为空");
				}
				cell = row.getCell(3);
				if (null != cell){
					uploadPrizeBean.setCount(Double.valueOf(getCell(cell))
							.intValue());
				}else{
					zeroCount++;
				}
                cell=row.getCell(4);
                if (null != cell){
                    uploadPrizeBean.setTitle(getCell(cell));
                }
                cell=row.getCell(5);
                if (null != cell){
                    uploadPrizeBean.setText(getCell(cell));
                }

				xmlTotalCount++;
				if(!uploadPrizeBeans.contains(uploadPrizeBean)){
					//System.out.println(uploadPrizeBean.toString());
					uploadPrizeBeans.add(uploadPrizeBean);
				}else{
					xmlRepeatCount ++ ;
				}
			}
		}
		resultBean.setAppid(appid);
		resultBean.setZeroCount(zeroCount);
		resultBean.setBeans(uploadPrizeBeans);
		resultBean.setXmlTotalCount(xmlTotalCount);
		resultBean.setXmlRepeatCount(xmlRepeatCount);
		
		return resultBean;
	}

	public static String getCell(XSSFCell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue() + "";
		case XSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case XSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case XSSFCell.CELL_TYPE_BLANK:
			return "";
		case XSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case XSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}
}
