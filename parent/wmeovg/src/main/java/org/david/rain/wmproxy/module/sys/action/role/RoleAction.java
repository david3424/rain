package org.david.rain.wmproxy.module.sys.action.role;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Role;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.FunctionMng;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.RoleMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@SuppressWarnings("unchecked")
public class RoleAction extends BaseAction {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LogMng logMng;
	
	public String list() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.findByName(query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<Role> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Role> it = list.iterator();
		
		while(it.hasNext()){
			Role role = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", role.getId());
			jo.put("name", role.getName());
			jo.put("note", role.getNote());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String all() throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.findByName(query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<Role> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<Role> it = list.iterator();
		
		while(it.hasNext()){
			Role role = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", role.getId());
			jo.put("name", role.getName());

			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String add() throws Exception{
		PrintWriter out = response.getWriter();
		if(bean != null)
			entityMng.save(bean);
		
		String operatorContent = "添加角色(" + bean.getName() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String get() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		bean = entityMng.findById(id);
		
		JSONObject json = new JSONObject();
		json.put("id", bean.getId());
		json.put("name", bean.getName());
		json.put("note", bean.getNote());
		
		out.write("[" + json.toString() + "]");
		return null;
	}
	
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		entityMng.deleteById(ids);
		
		String operatorContent = "删除角色(id=" + Arrays.toString(ids) + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String edit() throws Exception{
		PrintWriter out = response.getWriter();
		Role newRole = entityMng.findById(bean.getId());
		newRole.setName(bean.getName());
		newRole.setNote(bean.getNote());
		
		entityMng.update(newRole);
		
		String operatorContent = "更新角色(id=" + bean.getId() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}

	
	public String users() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		if(bean == null){
			json.put("users", new JSONArray());
			out.write(json.toString());
			return null;
		}
		
		Pagination pagination = entityMng.findUsers(bean.getId(), true, query, start, limit, sort, dir);	
		
		json.put("totalCount", pagination.getTotalCount());
		List<User> list = pagination.getList();
		
		JSONArray ja = new JSONArray();
		Iterator<User> it = list.iterator();
		
		while(it.hasNext()){
			User user = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", user.getId());
			jo.put("name", user.getName());
			jo.put("loginName", user.getLoginName());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String notusers() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		if(bean == null){
			json.put("users", new JSONArray());
			out.write(json.toString());
			return null;
		}
		
		Pagination pagination = entityMng.findUsers(bean.getId(), false, query, start, limit, sort, dir);	
		
		json.put("totalCount", pagination.getTotalCount());
		List<User> list = pagination.getList();
		
		JSONArray ja = new JSONArray();
		Iterator<User> it = list.iterator();
		
		while(it.hasNext()){
			User user = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", user.getId());
			jo.put("name", user.getName());
			jo.put("loginName", user.getLoginName());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String addUser() throws Exception{
		PrintWriter out = response.getWriter();
		Role r = entityMng.findById(bean.getId());
		
		for(int i=0; i<ids.length;i++){
			User user = userMng.findById(ids[i]);
			user.getRoles().add(r);
			r.getUsers().add(user);
			
			userMng.update(user);
			entityMng.update(r);
		}
		
		out.write("{success:true}");
		return null;
	}
	
	public String delUser() throws Exception{
		
		PrintWriter out = response.getWriter();
		Role r = entityMng.findById(bean.getId());
		
		for(int i=0; i<ids.length;i++){
			User user = userMng.findById(ids[i]);
			r.getUsers().remove(user);
			user.getRoles().remove(r);
			
			userMng.update(user);
			entityMng.update(r);
		}
		
		out.write("{success:true}");
		return null;
	}
	
	public String fucs() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		if(bean == null){
			json.put("records", new JSONArray());
			out.write(json.toString());
			return null;
		}
		
		Pagination pagination = entityMng.findFucs(bean.getId(), true, query, start, limit, sort, dir);	
		
		json.put("totalCount", pagination.getTotalCount());
		List<Function> list = pagination.getList();
		
		JSONArray ja = new JSONArray();
		Iterator<Function> it = list.iterator();
		
		while(it.hasNext()){
			Function fuc = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", fuc.getId());
			jo.put("name", fuc.getName());
			jo.put("model", fuc.getModel().getName());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String notfucs() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		if(bean == null){
			json.put("records", new JSONArray());
			out.write(json.toString());
			return null;
		}
		
		Pagination pagination = entityMng.findFucs(bean.getId(), false, query, start, limit, sort, dir);	
		
		json.put("totalCount", pagination.getTotalCount());
		List<Function> list = pagination.getList();
		
		JSONArray ja = new JSONArray();
		Iterator<Function> it = list.iterator();
		
		while(it.hasNext()){
			Function fuc = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", fuc.getId());
			jo.put("name", fuc.getName());
			jo.put("model", fuc.getModel().getName());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String addFuc() throws Exception{
		PrintWriter out = response.getWriter();
		Role r = entityMng.findById(bean.getId());
		
		for(int i=0; i<ids.length;i++){
			Function fuc = functionMng.findById(ids[i]);
			fuc.getRoles().add(r);
			r.getFunctions().add(fuc);
			
			functionMng.update(fuc);
			entityMng.update(r);
		}
		
		out.write("{success:true}");
		return null;
	}
	
	public String delFuc() throws Exception{
		
		PrintWriter out = response.getWriter();
		Role r = entityMng.findById(bean.getId());
		
		for(int i=0; i<ids.length;i++){
			Function fuc = functionMng.findById(ids[i]);
			r.getFunctions().remove(fuc);
			fuc.getRoles().remove(r);
			
			functionMng.update(fuc);
			entityMng.update(r);
		}
		
		out.write("{success:true}");
		return null;
	}
	

	public void setBean(Role bean) {
		this.bean = bean;
	}

	public Role getBean() {
		return bean;
	}



	private Role bean;
	
	@Autowired
	private RoleMng entityMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private FunctionMng functionMng;
}
