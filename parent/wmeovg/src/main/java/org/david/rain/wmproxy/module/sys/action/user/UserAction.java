package org.david.rain.wmproxy.module.sys.action.user;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Role;
import org.david.rain.wmproxy.module.sys.entity.User;
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
import java.util.Set;

public class UserAction extends BaseAction {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LogMng logMng;
	
	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = userMng.findByName(query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
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
			jo.put("note", user.getNote());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String add() throws Exception{
		PrintWriter out = response.getWriter();
		
		if(null != userMng.getByLoginName(bean.getLoginName().trim())){
			out.write("{success:false,msg:'用户名重复，请重新填写用户名。'}");
			return null;
		}
			
		if(bean != null){
			bean.setLoginName(bean.getLoginName().trim());
			userMng.save(bean);
		}
		
		String operatorContent = "添加用户(" + bean.getLoginName() + " " + bean.getName() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String get() throws Exception{
		
		PrintWriter out = response.getWriter();
		
		bean = userMng.findById(id);
		
		JSONObject json = new JSONObject();
		json.put("id", bean.getId());
		json.put("name", bean.getName());
		json.put("loginName", bean.getLoginName());
		json.put("note", bean.getNote());
		
		out.write("[" + json.toString() + "]");
		return null;
	}
	
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		for(int i=0; i<ids.length;i++){
			User user = userMng.findById(ids[i]);
			Set<Role> roles = user.getRoles();
			for(Role r: roles){
				r.getUsers().remove(user);
				user.getRoles().remove(r);
				
				userMng.update(user);
				roleMng.update(r);
			}
		}
		
		userMng.deleteById(ids);
		
		String operatorContent = "删除用户(id=" + Arrays.toString(ids) + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String edit() throws Exception{
		PrintWriter out = response.getWriter();
		
		User newUser = userMng.findById(bean.getId());
		
		if(!bean.getLoginName().trim().equalsIgnoreCase(newUser.getLoginName()) && null != userMng.getByLoginName(bean.getLoginName())){
			out.write("{success:false,msg:'用户名重复，请重新填写用户名。'}");
			return null;
		}
		
		newUser.setName(bean.getName());
		newUser.setLoginName(bean.getLoginName().trim());
		newUser.setNote(bean.getNote());
		
		userMng.update(newUser);
		
		String operatorContent = "更新用户(id=" + bean.getId() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}
	
	public String getCurrentUser() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		User user = userMng.getByLoginName(loginName);
		
		out.write(user.getName());
		return null;
	}

	public User getBean() {
		return bean;
	}

	public void setBean(User bean) {
		this.bean = bean;
	}


	private User bean;
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private RoleMng roleMng;
}
