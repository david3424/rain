package org.david.rain.wmproxy.module.config.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageBrowser;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageBrowser;
import org.david.rain.wmproxy.module.config.dao.ClientInfoDao;
import org.david.rain.wmproxy.module.config.dao.GameDao;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.entity.Game;
import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class WhiteListAction extends BaseAction {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserMng userMng;
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private GameDao gameDao;
	@Autowired
	private LogMng logMng;

	@SuppressWarnings("unchecked")
	public String list() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		Pagination pagination = entityMng.findByName(entity, start, limit, sort,
				dir);

		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());

		List<WhiteList> list = pagination.getList();
		JSONArray ja = new JSONArray();

		Iterator<WhiteList> it = list.iterator();

		while (it.hasNext()) {
			WhiteList entity = it.next();
			ClientInfo clientInfo = entity.getClientInfo();
			JSONObject jo = new JSONObject();
			jo.put("id", entity.getId());
			jo.put("clientName", clientInfo.getName());
			jo.put("game", entity.getGame().getName());
			jo.put("appid", entity.getAppid());
			jo.put("prizename", entity.getPrizename());
			jo.put("prizeid", entity.getPrizeid());
            //fhz 增加邮件标题和正文
            jo.put("title", entity.getTitle());
            jo.put("text", entity.getText());
			jo.put("count", entity.getCount());
			jo.put("sendCount", entity.getSendCount());
			jo.put("failCount", entity.getFailCount());

			jo.put("requestCount", requestMessageBrowser.getCount(clientInfo
					.getCid(), entity.getAppid(), entity.getPrizeid()));
			jo.put("callbackCount", callbackMessageBrowser.getCount(clientInfo
					.getCid(), entity.getAppid(), entity.getPrizeid()));

			jo.put("user", entity.getUser().getName());
			jo.put("createtime", DateUtil.format(entity.getCreatetime(),
                    DateUtil.DATETIME));
			jo.put("status", entity.getStatus());
			
			ja.add(jo);
		}

		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String ownlist() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		Pagination pagination = entityMng.findOwnListByName(loginName, query, start, limit, sort,
				dir);

		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());

		List<WhiteList> list = pagination.getList();
		JSONArray ja = new JSONArray();

		Iterator<WhiteList> it = list.iterator();

		while (it.hasNext()) {
			WhiteList entity = it.next();
			ClientInfo clientInfo = entity.getClientInfo();
			JSONObject jo = new JSONObject();
			jo.put("id", entity.getId());
			jo.put("clientName", clientInfo.getName());
			jo.put("game", entity.getGame().getName());
			jo.put("appid", entity.getAppid());
			jo.put("prizename", entity.getPrizename());
			jo.put("prizeid", entity.getPrizeid());
			jo.put("count", entity.getCount());
			jo.put("sendCount", entity.getSendCount());
			jo.put("failCount", entity.getFailCount());

			jo.put("requestCount", requestMessageBrowser.getCount(clientInfo
					.getCid(), entity.getAppid(), entity.getPrizeid()));
			jo.put("callbackCount", callbackMessageBrowser.getCount(clientInfo
					.getCid(), entity.getAppid(), entity.getPrizeid()));

			jo.put("user", entity.getUser().getName());
			jo.put("createtime", DateUtil.format(entity.getCreatetime(),
					DateUtil.DATETIME));
			jo.put("status", entity.getStatus());
			
			ja.add(jo);
		}

		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	

	public String add() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//System.out.println(request.getParameter("entity.clientInfo.id"));
		
		int clientId = Integer.parseInt(request.getParameter("entity.clientInfo.id"));
		int gameId = Integer.parseInt(request.getParameter("entity.game.id"));
		ClientInfo clientInfo = clientInfoDao.get(clientId);
		Game game = gameDao.get(gameId);
		int whiteListId = entityMng.getWhiteListIdByPrizeLog( clientId, entity.getAppid().trim(), entity
				.getPrizeid());
		if (whiteListId != 0) {
			out.write("{success:false, msg:'物品重复（客户端+应用标识+物品ID）'}");
			return null;
		}

		if (entity != null) {
			entity.setClientInfo(clientInfo);
			entity.setGame(game);
			String loginName = (String) request.getSession().getAttribute(
					User.LOGIN_NAME_KEY);
			User user = userMng.getByLoginName(loginName);
			entity.setUser(user);
			
			if (entity.getCount() == null) {
				entity.setCount(-1);
			}
			entity.setAppid(entity.getAppid().trim());
			entity.setSendCount(0);
			entity.setFailCount(0);
			entity.setCreatetime(new Date());
			entity.setStatus((byte)-1); // 默认拒绝
			entityMng.save(entity);
		}

		String operatorContent = "添加白名单(" + entity.toString() + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);

		out.write("{success:true}");
		return null;
	}

	public String get() throws Exception {

		PrintWriter out = response.getWriter();

		entity = entityMng.findById(id);

		JSONObject jo = new JSONObject();
		jo.put("id", entity.getId());
		jo.put("clientInfoId", entity.getClientInfo().getId());
		jo.put("gameId", entity.getGame().getId());
		jo.put("appid", entity.getAppid());
		jo.put("prizename", entity.getPrizename());
		jo.put("prizeid", entity.getPrizeid());
		jo.put("count", entity.getCount());

		if (entity.getCount() == -1) {
			jo.put("iscount", "on");
		}

		out.write(jo.toString());
		return null;
	}

	/*
	 * public String del() throws Exception{ PrintWriter out =
	 * response.getWriter();
	 * 
	 * entityMng.deleteById(ids);
	 * 
	 * String operatorContent = "删除白名单(id=" + Arrays.toString(ids) + ")";
	 * LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
	 * 
	 * out.write("{success:true}"); return null; }
	 */

	public String edit() throws Exception {
		PrintWriter out = response.getWriter();

		int clientId = Integer.parseInt(request.getParameter("entity.clientInfo.id"));
		int gameId = Integer.parseInt(request.getParameter("entity.game.id"));
		ClientInfo newClientInfo = clientInfoDao.get(clientId);
		Game newGame = gameDao.get(gameId);
		
		WhiteList orgWhiteList = entityMng.findById(entity.getId());
		
		//int clientInfoId = orgWhiteList.getClientInfo().getId();
		int newClientInfoId = newClientInfo.getId();
		String newAppid = entity.getAppid().trim();
		int newPrizeid = entity.getPrizeid();
		int whiteListId = entityMng.getWhiteListIdByPrizeLog(newClientInfoId,
				newAppid, newPrizeid);

		if (whiteListId != 0) {

			if(newClientInfoId != orgWhiteList.getClientInfo().getId()
						|| !newAppid.equals(orgWhiteList.getAppid()) 
						|| newPrizeid != orgWhiteList.getPrizeid()){
				out.write("{success:false, msg:'物品ID重复'}");
				return null;
			}
		}

		WhiteList newEntity = entityMng.findById(entity.getId());

		String before = newEntity.toString();

		if (entity.getCount() == null) {
			newEntity.setCount(-1);
			// newEntity.setSendCount(0);
			// newEntity.setFailCount(0);
		} else {
			newEntity.setCount(entity.getCount());
			if (newEntity.getSendCount() - newEntity.getFailCount() > entity
					.getCount()) {
				out.write("{success:false, msg:'发送总数不能少于(已请求数目 - 发送失败数目)'}");
				return null;
			}
		}

		newEntity.setClientInfo(newClientInfo);
		newEntity.setGame(newGame);
		newEntity.setAppid(newAppid);
		newEntity.setPrizename(entity.getPrizename());
		newEntity.setPrizeid(newPrizeid);
        newEntity.setTitle(entity.getTitle());//更新邮件标题和内容
        newEntity.setText(entity.getText());

		entityMng.update(newEntity);

		String after = newEntity.toString();

		String operatorContent = "更新白名单(" + before + " \n=>\n " + after + ")";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);

		out.write("{success:true}");
		return null;
	}

	public String updateStatus() throws Exception{
		PrintWriter out = response.getWriter();
		WhiteList newEntity = entityMng.findById(entity.getId());
		
		newEntity.setStatus(entity.getStatus());
		
		entityMng.update(newEntity);
		
		out.write("{success:true}");
		return null;
	}
	
	public String reject() throws Exception{
		PrintWriter out = response.getWriter();
		
		for(int id:ids){
			WhiteList newEntity = entityMng.findById(id);
			newEntity.setStatus((byte)-1);
			
			entityMng.update(newEntity);
		}
		out.write("{success:true}");
		return null;
	}
	
	public String accept() throws Exception{
		PrintWriter out = response.getWriter();
		
		for(int id:ids){
			WhiteList newEntity = entityMng.findById(id);
			newEntity.setStatus((byte)0);
			
			entityMng.update(newEntity);
		}
		out.write("{success:true}");
		return null;
	}
	
	public WhiteList getEntity() {
		return entity;
	}

	public void setEntity(WhiteList entity) {
		this.entity = entity;
	}

	private WhiteList entity;

	@Autowired
	private WhiteListMng entityMng;

	@Autowired
	private RequestMessageBrowser requestMessageBrowser;
	@Autowired
	private CallbackMessageBrowser callbackMessageBrowser;
}
