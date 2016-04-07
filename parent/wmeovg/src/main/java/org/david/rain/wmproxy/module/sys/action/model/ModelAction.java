package org.david.rain.wmproxy.module.sys.action.model;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Model;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.ModelMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ModelAction extends BaseAction {

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
				
		List<Model> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Model> it = list.iterator();
		
		while(it.hasNext()){
			Model model = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", model.getId());
			jo.put("name", model.getName());
			if(model.getParent() != null)
				jo.put("parentName", model.getParent().getName());
			jo.put("sort", model.getSort());
			jo.put("note", model.getNote());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String add() throws Exception{
		PrintWriter out = response.getWriter();
		if(model != null)
			entityMng.save(model);
		
		String operatorContent = "添加模块(" + model.getName() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String get() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		model = entityMng.findById(id);
		
		JSONObject json = new JSONObject();
		json.put("id", model.getId());
		if(model.getParent() != null){
			json.put("parentid", model.getParent().getId());
		}
		else
			json.put("parentid", 0);
		json.put("name", model.getName());
		json.put("sort", model.getSort());
		json.put("note", model.getNote());
		
		out.write("[" + json.toString() + "]");
		return null;
	}
	
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		entityMng.deleteById(ids);
		
		String operatorContent = "删除模块(id=" + Arrays.toString(ids) + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String edit() throws Exception{
		PrintWriter out = response.getWriter();
		Model newModel = entityMng.findById(model.getId());
		newModel.setName(model.getName());
		newModel.setSort(model.getSort());
		newModel.setNote(model.getNote());
		newModel.setParent(model.getParent());
		entityMng.update(newModel);
		
		String operatorContent = "更新模块(id=" + model.getId() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String parent() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.findByName(query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<Model> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Model> it = list.iterator();
		
		while(it.hasNext()){
			Model model = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", model.getId());
			jo.put("name", model.getName());
			
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
		/*
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		List<Model> models = entityMng.findAll();
		
		JSONArray ja = new JSONArray();
		JSONObject upJo = new JSONObject();
		upJo.put("id", "0");
		upJo.put("name", "顶层模块");
		ja.add(upJo);
		
		Iterator<Model> it = models.iterator();
		while(it.hasNext()){
			Model model = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", model.getId());
			jo.put("name", model.getName());
			
			ja.add(jo);
		}
		out.print("{models:" + ja.toString() + "}");
		return null;
		*/
	}
	

	public void setModel(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}



	private Model model;
	
	@Autowired
	private ModelMng entityMng;
}
