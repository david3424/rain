package org.david.rain.wmproxy.module.config.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.david.rain.webservice.ServiceInterface;
import org.david.rain.webservice.ServiceManage;
import org.david.rain.wmeovg.request.alg.Signature;
import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageBrowser;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageBrowser;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.david.rain.wmproxy.module.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ClientInfoAction extends BaseAction {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pagination pagination = entityMng.findByName(entity, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<ClientInfo> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<ClientInfo> it = list.iterator();
		
		while(it.hasNext()){
			ClientInfo entity = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", entity.getId());
			jo.put("cid", entity.getCid());
			jo.put("name", entity.getName());
			jo.put("privateKey", entity.getPrivateKey());
			jo.put("clientIp", entity.getClientIp());
			jo.put("priority", entity.getPriority());
			jo.put("rootUrl", entity.getRootUrl());
			jo.put("requestCount", requestMessageBrowser.getCount(entity.getCid()));
			jo.put("callbackCount", callbackMessageBrowser.getCount(entity.getCid()));
			jo.put("user", entity.getUser().getName());
			jo.put("createtime", DateUtil.format(entity.getCreatetime(), DateUtil.DATETIME));
			jo.put("status", entity.getStatus());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String ownlist() throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		
		Pagination pagination = entityMng.findOwnListByName(loginName, query, start, limit, sort, dir);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", pagination.getTotalCount());
				
		List<ClientInfo> list = pagination.getList();
		JSONArray ja = new JSONArray();
		
		Iterator<ClientInfo> it = list.iterator();
		
		while(it.hasNext()){
			ClientInfo entity = it.next();
			JSONObject jo = new JSONObject();
			jo.put("id", entity.getId());
			jo.put("cid", entity.getCid());
			jo.put("name", entity.getName());
			jo.put("privateKey", entity.getPrivateKey());
			jo.put("clientIp", entity.getClientIp());
			jo.put("priority", entity.getPriority());
			jo.put("rootUrl", entity.getRootUrl());
            try {
                jo.put("requestCount", requestMessageBrowser.getCount(entity.getCid()));
                jo.put("callbackCount", callbackMessageBrowser.getCount(entity.getCid()));
                logger.info("requestCount is {}",requestMessageBrowser.getCount(entity.getCid()));
                logger.info("callbackCount is {}",callbackMessageBrowser.getCount(entity.getCid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            jo.put("user", entity.getUser().getName());
			jo.put("createtime", DateUtil.format(entity.getCreatetime(), DateUtil.DATETIME));
			jo.put("status", entity.getStatus());
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String add() throws Exception{
		PrintWriter out = response.getWriter();
		if(entity != null){
			
			String loginName = (String) request.getSession().getAttribute(
					User.LOGIN_NAME_KEY);
			User user = userMng.getByLoginName(loginName);
			entity.setUser(user);
			
			entity.setCid(entityMng.genCid());
			entity.setPrivateKey(Signature.encryptBASE64(RandomUtil.getRandomString(12).getBytes()));
			entity.setCreatetime(new Date());
			entity.setStatus((byte)-1); // 默认拒绝
			entityMng.save(entity);
		}
		
		String operatorContent = "添加客户端(" + entity.getCid() + ")信息";
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
		jo.put("priority", entity.getPriority());
		jo.put("rootUrl", entity.getRootUrl());
		jo.put("privateKey", entity.getPrivateKey().trim());
		jo.put("clientIp", entity.getClientIp());
		
		out.write("[" + jo.toString() + "]");
		return null;
	}
	/*
	public String del() throws Exception{
		PrintWriter out = response.getWriter();
		
		entityMng.deleteById(ids);
		
		String operatorContent = "删除客户端(id=" + Arrays.toString(ids) + ")信息";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}*/
	
	public String edit() throws Exception{
		PrintWriter out = response.getWriter();
		
		ClientInfo newEntity = entityMng.findById(entity.getId());
		
		String before = newEntity.toString();
		
		newEntity.setName(entity.getName());
		newEntity.setPriority(entity.getPriority());
		newEntity.setRootUrl(entity.getRootUrl().trim());
		newEntity.setClientIp(entity.getClientIp().trim());
		
		entityMng.update(newEntity);
		
		String after = newEntity.toString();
		
		String operatorContent = "更新客户端(" + before + "\n=>\n" + after + ")信息";
		LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
		
		out.write("{success:true}");
		return null;
	}

	public String testUrl() throws Exception{
		PrintWriter out = response.getWriter();
		ClientInfo clientInfo = entityMng.findById(entity.getId());
				
		//String callbackUrl = clientInfo.getRootUrl() + "/servlet/wmeovg/callback";
		String callbackUrl = clientInfo.getRootUrl();
        logger.warn("callbackUrl:" + callbackUrl);

        @SuppressWarnings("unused")
        ServiceInterface activityService = ServiceManage.serviceInterface;

		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(callbackUrl);
		// 设置请求超时时间(ms)
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
		
		int statusCode = -1;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("客户端回调接口测试连接超时" + ex);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		
		out.print(statusCode);
		return null; 
	}
	
	public String updateStatus() throws Exception{
		PrintWriter out = response.getWriter();
		ClientInfo clientInfo = entityMng.findById(entity.getId());
		
		clientInfo.setStatus(entity.getStatus());
		
		entityMng.update(clientInfo);
		
		out.write("{success:true}");
		return null;
	}
	
	public String reject() throws Exception{
		PrintWriter out = response.getWriter();
		
		for(int id:ids){
			ClientInfo clientInfo = entityMng.findById(id);
			clientInfo.setStatus((byte)-1);
			
			entityMng.update(clientInfo);
		}
		out.write("{success:true}");
		return null;
	}
	
	public String accept() throws Exception{
		PrintWriter out = response.getWriter();
		
		for(int id:ids){
			ClientInfo clientInfo = entityMng.findById(id);
			clientInfo.setStatus((byte)0);
			
			entityMng.update(clientInfo);
		}
		out.write("{success:true}");
		return null;
	}
	
	public ClientInfo getEntity() {
		return entity;
	}

	public void setEntity(ClientInfo entity) {
		this.entity = entity;
	}

	public File getKeystore() {
		return keystore;
	}

	public void setKeystore(File keystore) {
		this.keystore = keystore;
	}

	public String getKeystoreFileName() {
		return keystoreFileName;
	}

	public void setKeystoreFileName(String keystoreFileName) {
		this.keystoreFileName = keystoreFileName;
	}

	public String getKeystoreContentType() {
		return keystoreContentType;
	}

	public void setKeystoreContentType(String keystoreContentType) {
		this.keystoreContentType = keystoreContentType;
	}

	private File keystore;
    private String keystoreFileName;
    private String keystoreContentType;

	private ClientInfo entity;
	
	@Autowired
	private ClientInfoMng entityMng;
	
	@Autowired
	private UserMng userMng;
	
	@Autowired
	private LogMng logMng;
	
	@Autowired
	private RequestMessageBrowser requestMessageBrowser;
	@Autowired
	private CallbackMessageBrowser callbackMessageBrowser;
}
