package org.david.rain.wmproxy.module.sys.action.tree;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Model;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.FunctionMng;
import org.david.rain.wmproxy.module.sys.manager.ModelMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
/**
 * Extjs TreeLoader 获取节点下的一级目录
 * @author gameuser
 *
 */
public class NodeAction extends BaseAction {
	
	public String execute() throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		Set<Function> userfucs = userMng.getUserFunctions(loginName);
		Set<Model> userModels = functionMng.getFunctionSetModels(userfucs);
		
		Model curModel = modelMng.findById(node);
		Set<Model> models = curModel.getChild();
		Set<Function> functions = curModel.getFunctions();
		
		Set<Node> nodes = new TreeSet<Node>();
		
		Iterator<Model> itModel = models.iterator();
		while(itModel.hasNext()){
			Model model = itModel.next();
			Node node = new Node(model.getId(), model.getSort(), model.getName(), false, null, null);
			if(userModels.contains(model)){
				nodes.add(node);
			}
		}
		
		Iterator<Function> itFunc = functions.iterator();
		while(itFunc.hasNext()){
			Function fuc = itFunc.next();
			Node node = new Node(fuc.getId(), fuc.getSort(), fuc.getName(), true, fuc.getSrc(), fuc.getTarget());
			if(userfucs.contains(fuc)){
				nodes.add(node);
			}
		}
		
		Iterator<Node> itNodes = nodes.iterator();
		JSONArray json = new JSONArray();
		while(itNodes.hasNext()){
			Node node = itNodes.next();
			JSONObject jo = new JSONObject();
			if(node.leaf)
				jo.put("id", "function_" + node.id);
			else
				jo.put("id", node.id);
			jo.put("text", node.text);
			jo.put("leaf", node.leaf);
			jo.put("url", node.url);
			jo.put("target", node.target);
			
			json.add(jo);
		}
		
		out.write(json.toString());
		return null;
	}

	
	public void setNode(Integer node) {
		this.node = node;
	}


	public Integer getNode() {
		return node;
	}

	/**
	 * TreeLoader请求参数
	 */
	private Integer node;
	
	@Autowired
	private ModelMng modelMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private FunctionMng functionMng;
}
