package org.david.rain.wmproxy.module.sys.action.tree;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Model;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class TreeAction extends BaseAction {

	/**
	 * 初始化个人菜单
	 * 
	 * @return
	 */
	public String execute() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		Set<Function> allFunctions = userMng.getUserFunctions(loginName);
		
		// 功能所属顶级模块以及模块下的一级菜单
		Set<Node> nodes = new TreeSet<Node>();
		Iterator<Function> itFuc = allFunctions.iterator();
		while (itFuc.hasNext()) {
			Function fuc = itFuc.next();
			Model model = fuc.getModel();
			assert (model != null);
			//注： 默认 model.id = 1是顶层模块
			while(model.getParent() != null && model.getParent().getId() > 1)
				model = model.getParent();
			Node node = new Node(model.getId(),model.getSort(), model.getName(), false, null, null);
			nodes.add(node);
		}
		
		JSONArray json = new JSONArray();
		
		Iterator<Node> it = nodes.iterator();
		while(it.hasNext()){
			Node node = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", node.id);
			jo.put("text", node.text);
			jo.put("leaf", node.leaf);
			jo.put("url", node.url);
			
			json.add(jo);
		}
		
		out.write(json.toString());
		return null;
	}

	@Autowired
	private UserMng userMng;
}
