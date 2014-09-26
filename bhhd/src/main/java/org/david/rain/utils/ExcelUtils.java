package org.david.rain.utils;

import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;


public class ExcelUtils {
	
    /**
     * 
     * @param workbook
     * @return
     */
	public static Map<String, CellStyle> getCellStyles(Workbook workbook) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		DataFormat df = workbook.createDataFormat();

		//header style
		CellStyle style;
		Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        //normal data
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("normal", style);

        //error data
        Font errorFont = workbook.createFont();
        errorFont.setColor(IndexedColors.RED.getIndex());
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setFont(errorFont);
        styles.put("error", style);

        //green data
        Font greenFont = workbook.createFont();
        greenFont.setColor(IndexedColors.GREEN.getIndex());
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setFont(greenFont);
        styles.put("green", style);

        //date style
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("yyyy-MM-dd"));
        styles.put("date", style);

        //date style
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat(DateUtils.YYYYMMDDHM));
        styles.put("date_2", style);

        //error date style
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat(DateUtils.YYYYMMDDHM));
        style.setFont(errorFont);
        styles.put("error_date_2", style);

        //number
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("###,###0"));
        styles.put("number", style);

        //number
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("###,###0.0"));
        styles.put("number_2", style);

		return styles;
	}

	public static  void createCell(Row row, int column, CellStyle style, int cellType, Object value) {
		Cell cell = row.createCell((short)column);
		if(style != null) {
			cell.setCellStyle(style);
		}
		switch (cellType) {
			case Cell.CELL_TYPE_BLANK:
				break;

			case Cell.CELL_TYPE_STRING:
				if(value != null) {
					cell.setCellValue(value.toString());
				}
				break;

			case Cell.CELL_TYPE_NUMERIC:
				cell.setCellType(cellType);
				if(value != null) {
					cell.setCellValue(Double.parseDouble(value.toString()));
				}
				break;

			default:
				break;
		}
	}

    private static CellStyle createBorderedStyle(Workbook wb){
    	CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
}
