package org.david.rain.wmproxy.module.export.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import com.csvreader.CsvWriter;
import org.apache.commons.lang.StringUtils;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.service.action.PrizeLogSearchBean;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

/**
 * @ClassName PrizeLogAction
 * @Description 兑换记录导出CSV格式
 * @version 1.0
 * @date 2010-8-11 上午11:10:36
 */
public class PrizeLogExportAsCsvAction extends BaseAction implements Action {
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {

		Finder finder = PrizeLogSearchBean.getFinder(searchBean, "requestTime", "desc");
		int totalCount = entityMng.getTotalCount(finder);
		
		int sheetCount = 1000;
		int sheetIndex = totalCount / sheetCount;
		if(totalCount % sheetCount > 0){
			++sheetIndex;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CsvWriter writer = new CsvWriter( baos, ',', Charset.forName("GBK"));
		
		//writer.write("流水号");
		//writer.write("客户端");
		//writer.write("所属应用");
		writer.write("账号");
		writer.write("角色名/ID");
		writer.write("服务器");
		writer.write("物品ID");
		//writer.write("发送个数");
		//writer.write("请求时间");
		//writer.write("发送时间");
		writer.write("发送状态");
		//writer.write("回调时间");
		//writer.write("回调状态");
		//writer.write("IP");
		writer.endRecord();
		
		for (int i = 1; i <= sheetIndex; i++) {
			Pagination pagination = entityMng.pageBySearchBean(searchBean, (i-1)*sheetCount,
					sheetCount, "requestTime", "desc");
			List<PrizeLog> list = pagination.getList();
			for(PrizeLog entity: list){
				
				//writer.write(entity.getGid());
				//writer.write(clientInfoMng.getClientInfoByCid(entity.getCid()).getName());
				//writer.write(entity.getAppid());
				writer.write(entity.getAccount());
				if(StringUtils.isNotEmpty(entity.getRolename()))
					writer.write(entity.getRolename());
				else
					writer.write(String.valueOf(entity.getRoleid()));
				writer.write(String.valueOf(entity.getZoneid()));
				writer.write(String.valueOf(entity.getPrizeid()));
				/*writer.write(String.valueOf(entity.getCount()));
				if(entity.getRequestTime() != null)
				writer.write(DateUtil.format(entity.getRequestTime(),
						DateUtil.DATETIME));
				else
					writer.write("");
				if(entity.getSendTime() != null)
				writer.write(DateUtil.format(entity.getSendTime(),
						DateUtil.DATETIME));
				else
					writer.write("");*/
				if(entity.getSendStatus() != null)
				writer.write(String.valueOf(entity.getSendStatus()));
				else
					writer.write("");
				/*
				if(entity.getCallbackTime() != null)
				writer.write(DateUtil.format(entity.getCallbackTime(),
						DateUtil.DATETIME));
				else
					writer.write("");
				if(entity.getCallbackHttpStatus() != null)
					writer.write(String.valueOf(entity.getCallbackHttpStatus()));
				else
					writer.write("");
				
				writer.write(entity.getIp());
				*/
				writer.endRecord();
			}
		}
		writer.close();
		setInputStream(new ByteArrayInputStream(baos.toByteArray()));
		
		try {
			fileName = new String(("prizelog"
					+ DateUtil.format(new Date(), "yyyy_MM_dd") + ".csv")
					.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String operatorContent = "导出兑换记录CSV(查询条件" + searchBean.toString() + ")";
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

	public void setSearchBean(PrizeLogSearchBean searchBean) {
		this.searchBean = searchBean;
	}

	public PrizeLogSearchBean getSearchBean() {
		return searchBean;
	}

	private PrizeLogSearchBean searchBean;
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
