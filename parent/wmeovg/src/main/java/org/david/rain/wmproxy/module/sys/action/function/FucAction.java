package org.david.rain.wmproxy.module.sys.action.function;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.manager.FunctionMng;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class FucAction extends BaseAction {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private LogMng logMng;
	
	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.findByName(query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<Function> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Function> it = list.iterator();
		
		while(it.hasNext()){
			Function bean = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", bean.getId());
			jo.put("name", bean.getName());
			jo.put("src", bean.getSrc());
			jo.put("sort", bean.getSort());
			jo.put("note", bean.getNote());
			if(bean.getModel() != null)
				jo.put("model", bean.getModel().getName());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(callback + "("+ json.toString() +");");
		return null;
	}
	
	public String add() throws Exception{
		PrintWriter out = response.getWriter();
		if(bean != null)
			entityMng.save(bean);
		
		String operatorContent = "添加功能(" + bean.getName() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String get() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		bean = entityMng.findById(id);
		
		JSONObject jo = new JSONObject();
		jo.put("id", bean.getId());
		jo.put("name", bean.getName());
		jo.put("src", bean.getSrc());
		jo.put("sort", bean.getSort());
		jo.put("note", bean.getNote());
		if(bean.getModel() != null){
			jo.put("parentid", bean.getModel().getId());
		}
		else
			jo.put("parentid", 0);
		
		out.write("[" + jo.toString() + "]");
		return null;
	}
	
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		entityMng.deleteById(ids);
		
		String operatorContent = "删除功能(id=" + Arrays.toString(ids) + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String edit() throws Exception{
		PrintWriter out = response.getWriter();
		Function newEntity = entityMng.findById(bean.getId());
		newEntity.setName(bean.getName());
		newEntity.setSort(bean.getSort());
		newEntity.setSrc(bean.getSrc());
		newEntity.setNote(bean.getNote());
		newEntity.setModel(bean.getModel());
		
		entityMng.update(newEntity);
		
		String operatorContent = "更新功能(id=" + bean.getId() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}

	public void setBean(Function bean) {
		this.bean = bean;
	}

	public Function getBean() {
		return bean;
	}

	private Function bean;
	
	@Autowired
	private FunctionMng entityMng;
}
