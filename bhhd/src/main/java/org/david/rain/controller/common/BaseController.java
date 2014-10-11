package org.david.rain.controller.common;

import org.david.rain.security.tool.SessionUser;
import org.david.rain.utils.ExcelUtils;
import org.david.rain.utils.JodaUtils;
import org.david.rain.utils.PoiUtils;
import org.david.rain.utils.vo.DataElements;
import org.david.rain.model.GameBean;
import flexjson.JSONSerializer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

	protected void exportExcel(List<String> columnnames,
			List<List<String>> datas, String filename,
			HttpServletResponse response) throws IOException {
		if (!filename.endsWith(".xls")) {
			filename += ".xls";
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename="
				+ filename);
		response.setBufferSize(5000);
		OutputStream out = response.getOutputStream();
		getExcelWorkbook(columnnames, datas).write(out);
		out.flush();
		out.close();
	}

	public Workbook getExcelWorkbook(List<String> columnnames,
			List<List<String>> datas) {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			Map<String, CellStyle> wbStyles = ExcelUtils
					.getCellStyles(workbook);
			if (datas != null && datas.size() > 0) {
				int perNum = 30000;
				int len = datas.size();
				if (len > 100000) {
					len = 100000;
				}
				int sheetNum = len / perNum + 1;
				for (int i = 0; i < sheetNum; i++) {
					int startNo = perNum * i;
					int endNo = perNum * (i + 1);
					if (endNo > len) {
						endNo = len;
					}
					generalSheet(workbook, wbStyles, "sheet" + i, startNo,
							endNo, columnnames, datas);
				}

			} else {
				generalSheet(workbook, wbStyles, "sheet", 0, 0, columnnames,
						datas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void generalSheet(Workbook workbook,
			Map<String, CellStyle> wbStyles, String sheetName, int start,
			int end, List<String> columnnames, List<List<String>> datas) {
		Sheet sheet = workbook.createSheet(sheetName);

		// 设置列宽
		int col = 0;
		if (columnnames != null) {
			for (String columnname : columnnames) {
				sheet.setColumnWidth((short) col++, 5000);
			}
		}

		if (end > start) {
			// 创建第一行标题
			Row row = sheet.createRow((short) 0);
			col = 0;
			for (String columnname : columnnames) {
				ExcelUtils.createCell(row, (short) col++,
						wbStyles.get("header"), XSSFCell.CELL_TYPE_STRING,
						columnname);
			}
			int lineNum = 1;
			for (int i = start; i < end; i++) {
				List<String> rowDatas = datas.get(i);
				Row rowl = sheet.createRow((short) (lineNum++));
				col = 0;
				for (String data : rowDatas) {
					ExcelUtils.createCell(rowl, (short) col++,
							wbStyles.get("normal"), XSSFCell.CELL_TYPE_STRING,
							data);
				}
			}
		}
	}

	
	protected void printToHtml(HttpServletResponse response, Map jsonMap) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();
			
			//Flex json serializer
			JSONSerializer jsonSerializer = new JSONSerializer();

			out.println(jsonSerializer.deepSerialize(jsonMap));

			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void printToJson(HttpServletResponse response, String jsonStr) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();

			out.println(jsonStr);

			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * printToJson
	 * <p>
	 * print the json object
	 * 
	 * @param response
	 * @param jsonMap
	 */
	protected void printToJson(HttpServletResponse response, Map jsonMap) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();

			// Flex json serializer
			JSONSerializer jsonSerializer = new JSONSerializer();

			out.println(jsonSerializer.deepSerialize(jsonMap));

			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String objectToJsonString(Object obj) {
		if (null == obj) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(obj);
		return jsonArray.toString();
	}

	/**
	 * 将DataTables中的参数json串转为Map输出
	 * 
	 * @param dtjson
	 * @return
	 */
	protected Map<String, Object> jsonToMap(String dtjson) {

		JSONArray jsonArray = JSONArray.fromObject(dtjson);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (Object object : jsonArray) {
			JSONObject jsonObject = JSONObject.fromObject(object);
			map.put(jsonObject.getString("name"), jsonObject.getString("value"));
		}
		return map;
	}

	protected void printDataTables(HttpServletResponse response,
			Integer record_count, List list) {
		StringBuilder data = new StringBuilder("{");
		data.append("\"iTotalRecords\" : " + record_count + ",");
		data.append("\"iTotalDisplayRecords\" : " + record_count + ",");
		data.append("\"aaData\" : ");
		data.append(JSONArray.fromObject(list).toString());
		data.append("}");
		printToJson(response, data.toString());
	}

	protected String constructDataTablesData(Integer record_count, List list) {
		StringBuilder data = new StringBuilder("{");
		data.append("\"iTotalRecords\" : " + record_count + ",");
		data.append("\"iTotalDisplayRecords\" : " + record_count + ",");
		data.append("\"aaData\" : ");
		data.append(JSONArray.fromObject(list).toString());
		data.append("}");

		return data.toString();
	}

	/**
	 * exportExcel
	 * <p>
	 * export the excel, please notice this, this only can export some simple
	 * excel
	 * 
	 * @param response
	 * @param elements
	 * @param list
	 * @param sheetName
	 * @param fileName
	 */
	protected void exportExcel(HttpServletResponse response,
			DataElements[] elements, List list, String sheetName,
			String fileName) {
		// create the excel work book
		HSSFWorkbook wb = new HSSFWorkbook();
		// create the sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// get the first row
		HSSFRow row = sheet.createRow(0);

		// get the style of title cell
		HSSFCellStyle titleStyle = PoiUtils.getTitleStyle(wb);
		// get the style of string cell
		HSSFCellStyle stringStyle = PoiUtils.getStringStyle(wb);
		// get the style of number cell
		HSSFCellStyle numberStyle = PoiUtils.getNumberStyle(wb);
		// get the style of time cell
		HSSFCellStyle timeStyle = PoiUtils.getTimeStyle(wb);

		// set the height of title row
		row.setHeight((short) 500);
		// count
		int count = 0;
		// cell of excel
		HSSFCell cell = null;

		if (null != elements && 0 < elements.length) {

			for (DataElements element : elements) {
				// get the cell, set the title
				cell = row.createCell(count);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(new HSSFRichTextString(element.getGbkName()));
				// set the width of this column
				sheet.setColumnWidth(count, element.getExcelColWidth());
				// add one by one
				count++;
			}

			// row count
			int rowCount = 1;
			if (null != list && !list.isEmpty()) {

				// parse data
				for (Object obj : list) {
					// get the row
					row = sheet.createRow(rowCount);

					// initialize count
					count = 0;

					for (DataElements element : elements) {
						// set the data
						cell = row.createCell(count);
						// cell value
						String value = "";
						if (DataElements.Type.Numeric == element.getType()) {
							cell.setCellStyle(numberStyle);
							value = PoiUtils
									.formatNumber(getContentByReflectProxy(
											element.getName(), obj));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Integer == element
								.getType()) {
							cell.setCellStyle(numberStyle);
							value = PoiUtils
									.formatInteger(getContentByReflectProxy(
											element.getName(), obj));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.PostiveInteger == element
								.getType()) {
							cell.setCellStyle(numberStyle);
							value = PoiUtils
									.formatPostiveInteger(getContentByReflectProxy(
											element.getName(), obj));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Varchar == element
								.getType()) {
							cell.setCellStyle(stringStyle);
							value = PoiUtils
									.noNullTrim(getContentByReflectProxy(
											element.getName(), obj));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Time == element.getType()) {
							cell.setCellStyle(timeStyle);
							value = JodaUtils.getDefaultDateFormatter().print(
									new DateTime(getContentByReflectProxy(
											element.getName(), obj)));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Date == element.getType()) {
							cell.setCellStyle(timeStyle);
							value = JodaUtils.getDefaultDateFormatter().print(
									new DateTime(getContentByReflectProxy(
											element.getName(), obj)));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Month == element.getType()) {
							cell.setCellStyle(timeStyle);
							value = JodaUtils.getNormalMonthFormatter().print(
									new DateTime(getContentByReflectProxy(
											element.getName(), obj)));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Timestamp == element
								.getType()) {
							cell.setCellStyle(timeStyle);
							value = JodaUtils.getTimeFormatter().print(
									new DateTime(getContentByReflectProxy(
											element.getName(), obj)));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else if (DataElements.Type.Percent == element
								.getType()) {
							cell.setCellStyle(timeStyle);
							value = PoiUtils
									.noNullTrim(getContentByReflectProxy(
											element.getName(), obj) + "%");
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						} else {
							cell.setCellStyle(stringStyle);
							value = PoiUtils
									.noNullTrim(getContentByReflectProxy(
											element.getName(), obj));
							cell.setCellValue(new HSSFRichTextString(""
									.equals(value) ? "" : (element
									.getGridPrefix() + value + element
									.getGridSuffix())));
						}

						// add one by one
						count++;
					}
					// add one by one for getting the row
					rowCount++;
				}
			}
		}

		// download setting
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xls");

		// initialize the outputStream
		OutputStream out = null;
		try {
			// get the outputStream from response
			out = response.getOutputStream();
			// writing the excel
			wb.write(out);

			// close the outputStream
			out.flush();
			out.close();
		} catch (IOException e) {
			// error, print the error
			e.printStackTrace();
		} finally {
			// close the outputStream
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * getContentByReflectProxy
	 * <p>
	 * get the content using reflect proxy
	 * 
	 * @param name
	 * @param obj
	 * @return
	 */
	private Object getContentByReflectProxy(String name, Object obj) {
		try {
			Class clazz = obj.getClass();
			Field f = clazz.getDeclaredField(name);
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
			Method readerM = pd.getReadMethod();

			return readerM.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

    /**
     * get gameId by Game Shortname
     * @param request
     * @param shortName
     * @return
     */
	protected Integer getGameId(HttpServletRequest request, String shortName) {
		HttpSession httpSession = request.getSession(true);
		Integer id = -1;
		SessionUser user = (SessionUser) httpSession
				.getAttribute(SessionUser.SESSION_ROOT_KEY);
		GameBean game = user.getGameMap().get(shortName);
		if (game != null)
			id = game.getGameId();

		return id;
	}
	protected String getGameName(HttpServletRequest request,String shortName) {
		HttpSession httpSession = request.getSession(true);
		SessionUser user = (SessionUser)httpSession.getAttribute(SessionUser.SESSION_ROOT_KEY);
				 GameBean game = user.getGameMap().get(shortName);
				 if (null!=game) {
					return game.getGameName();
				}
				 return "";
	
	}
	
}
