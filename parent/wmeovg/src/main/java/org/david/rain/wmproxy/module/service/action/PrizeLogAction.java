package org.david.rain.wmproxy.module.service.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.springframework.beans.factory.annotation.Autowired;

public class PrizeLogAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.pageBySearchBean(searchBean, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<PrizeLog> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<PrizeLog> it = list.iterator();
		
		while(it.hasNext()){
			PrizeLog entity = it.next();
			JSONObject jo = new JSONObject();
			jo.put("gid", entity.getGid());
			jo.put("cid", clientInfoMng.getClientInfoByCid(entity.getCid()).getName());
			jo.put("appid", entity.getAppid());
			jo.put("zoneid", entity.getZoneid());
			jo.put("account", entity.getAccount());
			jo.put("rolename", entity.getRolename());
			jo.put("roleid", entity.getRoleid());
			jo.put("prizeid", entity.getPrizeid());
			jo.put("count", entity.getCount());
			jo.put("priority", entity.getPriority());
			jo.put("callback", entity.getCallback());
			jo.put("sendStatus", entity.getSendStatus());
			jo.put("processCount", entity.getProcessCount());
			jo.put("callbackStatus", entity.getCallbackStatus());
			jo.put("requestTime", DateUtil.format(entity.getRequestTime(), DateUtil.DATETIME));
			jo.put("sendTime", DateUtil.format(entity.getSendTime(), DateUtil.DATETIME));
			jo.put("prizeResendCount", entity.getPrizeResendCount());
			jo.put("callbackTime", DateUtil.format(entity.getCallbackTime(), DateUtil.DATETIME));
			jo.put("callbackHttpStatus", entity.getCallbackHttpStatus());
			jo.put("callbackCount", entity.getCallbackCount());
			//jo.put("ip", entity.getIp());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String get() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		entity = entityMng.findById(entity.getGid());
		
		JSONObject jo = new JSONObject();
		jo.put("gid", entity.getGid());
		jo.put("cid", clientInfoMng.getClientInfoByCid(entity.getCid()).getName() + "(" + entity.getCid() + ")");
		jo.put("appid", entity.getAppid());
		jo.put("zoneid", entity.getZoneid());
		jo.put("account", entity.getAccount());
		jo.put("rolename", entity.getRolename());
		jo.put("roleid", entity.getRoleid());
		jo.put("prizeid", entity.getPrizeid());
		jo.put("count", entity.getCount());
		jo.put("priority", entity.getPriority());
		jo.put("callback", entity.getCallback());
		jo.put("sendStatus", entity.getSendStatus());
		jo.put("processCount", entity.getProcessCount());
		jo.put("callbackStatus", entity.getCallbackStatus());
		jo.put("requestTime", DateUtil.format(entity.getRequestTime(), DateUtil.DATETIME));
		jo.put("sendTime", DateUtil.format(entity.getSendTime(), DateUtil.DATETIME));
		jo.put("prizeResendCount", entity.getPrizeResendCount());
		jo.put("callbackTime", DateUtil.format(entity.getCallbackTime(), DateUtil.DATETIME));
		jo.put("callbackHttpStatus", entity.getCallbackHttpStatus());
		jo.put("callbackCount", entity.getCallbackCount());
		jo.put("ip", entity.getIp());
		
		out.write(jo.toString());
		return null;
	}
	
	public void setSearchBean(PrizeLogSearchBean searchBean) {
		this.searchBean = searchBean;
	}
	public PrizeLogSearchBean getSearchBean() {
		return searchBean;
	}

	public void setEntity(PrizeLog entity) {
		this.entity = entity;
	}

	public PrizeLog getEntity() {
		return entity;
	}

	private PrizeLog entity;
	private PrizeLogSearchBean searchBean;
	@Autowired
	private PrizeLogMng entityMng;
	@Autowired
	private ClientInfoMng clientInfoMng;
}
