package org.david.rain.wmproxy.module.sys.action.log;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Log;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.springframework.beans.factory.annotation.Autowired;


public class LogAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.findByName(query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<Log> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Log> it = list.iterator();
		
		while(it.hasNext()){
			Log bean = it.next();
			User user = bean.getUser();
			JSONObject jo = new JSONObject();
			jo.put("id", bean.getId());
			jo.put("name", user.getName());
			jo.put("loginName", user.getLoginName());
			jo.put("content", bean.getContent());
			jo.put("createtime", bean.getCreatetime());
			jo.put("ip", bean.getIp());
			
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String get() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		bean = entityMng.findById(id);
		//User user = bean.getUser();
		JSONObject jo = new JSONObject();
		jo.put("id", bean.getId());
		//jo.put("name", user.getName());
		//jo.put("loginName", user.getLoginName());
		jo.put("content", "<pre>" + bean.getContent() + "</pre>");
		//jo.put("createtime", bean.getCreatetime());
		//jo.put("ip", bean.getIp());
		
		out.write("[" + jo.toString() + "]");
		return null;
	}
	
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		entityMng.deleteById(ids);
		
		out.write("{success:true}");
		return null;
	}

	public void setBean(Log bean) {
		this.bean = bean;
	}

	public Log getBean() {
		return bean;
	}

	private Log bean;
	
	@Autowired
	private LogMng entityMng;
}
