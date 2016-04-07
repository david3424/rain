package org.david.rain.wmproxy.module.export.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @ClassName PrizeLogAction
 * @Description 兑换记录导出
 * @version 1.0
 * @date 2010-8-11 上午11:10:36
 */
public class PrizeLogSelectedExportAction extends BaseAction implements Action {

	public String execute() throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		int columnIndex = 0;
		/*sheet.setColumnWidth(0, 256*50);
		sheet.setColumnWidth(1, 256*8);
		sheet.setColumnWidth(2, 256*10);*/
		sheet.setColumnWidth(columnIndex++, 256*10);
		sheet.setColumnWidth(columnIndex++, 256*20);
		sheet.setColumnWidth(columnIndex++, 256*8);
		sheet.setColumnWidth(columnIndex++, 256*8);
		/*sheet.setColumnWidth(7, 256*10);
		sheet.setColumnWidth(8, 256*20);
		sheet.setColumnWidth(9, 256*20);*/
		sheet.setColumnWidth(columnIndex++, 256*10);
		/*sheet.setColumnWidth(11, 256*20);
		sheet.setColumnWidth(12, 256*10);
		sheet.setColumnWidth(13, 256*15);*/
		int rowIndex = 0;
		columnIndex = 0;
		HSSFRow row = sheet.createRow(rowIndex++);
		HSSFCell cell = row.createCell(columnIndex++);
		/*cell.setCellValue("流水号");
		cell = row.createCell(1);
		cell.setCellValue("客户端");
		cell = row.createCell(2);
		cell.setCellValue("所属应用");
		cell = row.createCell(3);		*/
		cell.setCellValue("账号");
		cell = row.createCell(columnIndex++);
		cell.setCellValue("角色名/ID");
		cell = row.createCell(columnIndex++);
		cell.setCellValue("服务器");
		cell = row.createCell(columnIndex++);
		cell.setCellValue("物品ID");
		cell = row.createCell(columnIndex++);	
		/*cell.setCellValue("发送个数");
		cell = row.createCell(8);
		cell.setCellValue("请求时间");
		cell = row.createCell(9);
		cell.setCellValue("发送时间");
		cell = row.createCell(10);*/
		cell.setCellValue("发送状态");
		/*cell = row.createCell(11);
		cell.setCellValue("回调时间");
		cell = row.createCell(12);
		cell.setCellValue("回调状态");
		cell = row.createCell(13);
		cell.setCellValue("IP");*/
		
		String prizelogGids = request.getParameter("prizelogGids");
		
		for(String gid :prizelogGids.split(",")){
			
			PrizeLog entity = entityMng.findById(gid);
			columnIndex = 0;
			
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(columnIndex++);
			/*cell.setCellValue(entity.getGid());
			cell = row.createCell(1);
			cell.setCellValue(clientInfoMng.getClientInfoByCid(entity.getCid()).getName());
			cell = row.createCell(2);
			cell.setCellValue(entity.getAppid());
			cell = row.createCell(3);*/
			cell.setCellValue(entity.getAccount());
			cell = row.createCell(columnIndex++);
			if(StringUtils.isNotEmpty(entity.getRolename()))
				cell.setCellValue(entity.getRolename());
			else
				cell.setCellValue(entity.getRoleid());
			cell = row.createCell(columnIndex++);
			cell.setCellValue(entity.getZoneid());
			cell = row.createCell(columnIndex++);
			cell.setCellValue(entity.getPrizeid());
			cell = row.createCell(columnIndex++);
			/*cell.setCellValue(entity.getCount());
			cell = row.createCell(8);
			if(entity.getRequestTime() != null)
			cell.setCellValue(DateUtil.format(entity.getRequestTime(),
					DateUtil.DATETIME));
			cell = row.createCell(9);
			if(entity.getSendTime() != null)
			cell.setCellValue(DateUtil.format(entity.getSendTime(),
					DateUtil.DATETIME));
			cell = row.createCell(10);*/
			if(entity.getSendStatus() != null)
			cell.setCellValue(entity.getSendStatus());
			/*cell = row.createCell(11);
			if(entity.getCallbackTime() != null)
			cell.setCellValue(DateUtil.format(entity.getCallbackTime(),
					DateUtil.DATETIME));
			cell = row.createCell(12);
			if(entity.getCallbackHttpStatus() != null)
				cell.setCellValue(entity.getCallbackHttpStatus());
			cell = row.createCell(13);
			cell.setCellValue(entity.getIp());*/
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);

		setInputStream(new ByteArrayInputStream(baos.toByteArray()));

		try {
			fileName = new String(("prizelog"+ DateUtil.format(new Date(), "yyyy_MM_dd") +".xls").getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String operatorContent = "导出选中的兑换记录(id=" + prizelogGids + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		return SUCCESS;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	private InputStream inputStream;

	private String fileName;
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	protected Integer[] ids;
	@Autowired
	private PrizeLogMng entityMng;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserMng userMng;
	@Autowired
	private ClientInfoMng clientInfoMng;
	@Autowired
	private LogMng logMng;
}
