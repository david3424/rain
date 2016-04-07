package org.david.rain.wmproxy.module.config.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.config.entity.Game;
import org.david.rain.wmproxy.module.config.manager.GameMng;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class GameAction extends BaseAction {

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
				
		List<Game> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Game> it = list.iterator();
		
		while(it.hasNext()){
			Game entity = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", entity.getId());
			jo.put("name", entity.getName());
			jo.put("serverName", entity.getServerName());
			jo.put("aid", entity.getAid());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String add() throws Exception{
		PrintWriter out = response.getWriter();
		if(entity != null){
			
			entity.setName(entity.getName().trim());
			entity.setServerName(entity.getServerName().trim());
			
			entityMng.save(entity);
		}
		String operatorContent = "添加游戏类别(" + entity.getName() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String get() throws Exception{
		
		PrintWriter out = response.getWriter();
		
		entity = entityMng.findById(id);
		
		JSONObject jo = new JSONObject();
		jo.put("id", entity.getId());
		jo.put("name", entity.getName());
		jo.put("serverName", entity.getServerName());
		jo.put("aid", entity.getAid());
		
		out.write("[" + jo.toString() + "]");
		return null;
	}
	/*
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		entityMng.deleteById(ids);
		
		String operatorContent = "删除游戏类别(id=" + Arrays.toString(ids) + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}*/
	
	public String edit() throws Exception{
		PrintWriter out = response.getWriter();
		
		Game newEntity = entityMng.findById(entity.getId());
		
		String before = newEntity.toString();
		
		newEntity.setName(entity.getName());
		newEntity.setServerName(entity.getServerName());
		newEntity.setAid(entity.getAid());
		
		entityMng.update(newEntity);
		
		String after = newEntity.toString();
		
		String operatorContent = "更新游戏类别(" + before + " \n=>\n " + after + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}

	public Game getEntity() {
		return entity;
	}

	public void setEntity(Game entity) {
		this.entity = entity;
	}



	private Game entity;
	
	@Autowired
	private GameMng entityMng;
}
