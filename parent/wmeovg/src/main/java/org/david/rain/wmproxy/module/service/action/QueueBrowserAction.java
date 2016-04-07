package org.david.rain.wmproxy.module.service.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageBrowser;
import org.david.rain.wmproxy.module.activemq.dlq.DlqMessageBrowser;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageBrowser;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.util.List;


/**
 * @ClassName QueueBrowserAction
 * @Description 查看JMS队列情况
 * @version 1.0
 * @date 2010-8-11 上午11:25:50
 */
public class QueueBrowserAction extends BaseAction {
	
	@Autowired
	private RequestMessageBrowser requestMessageBrowser;
	@Autowired
	private CallbackMessageBrowser callbackMessageBrowser;
	
	@Autowired
	private DlqMessageBrowser dlqMessageBrowser;
	@Autowired
	private ClientInfoMng clientInfoMng;
	
	/*public static String jmxDomain = "jmx.domain";
	public static int connectorPort = 1099;
	public static String connectorPath = "/jmxrmi";*/
	
	public String execute() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//out.write("请求队列:" + requestMessageBrowser.getCount() + "   回调队列:" +  callbackMessageBrowser.getCount() + "  异常：" + dlqMessageBrowser.getCount());
		//out.write("请求队列:" + requestMessageBrowser.getCount() + "   回调队列:" +  callbackMessageBrowser.getCount());
		
		/*JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:" + connectorPort
						+ connectorPath);
		JMXConnector connector = JMXConnectorFactory.connect(url, null);
		connector.connect();
		MBeanServerConnection connection = connector.getMBeanServerConnection();

		// 需要注意的是，这里的jms-broker必须和上面配置的名称相同
		ObjectName name = new ObjectName(jmxDomain
				+ ":BrokerName=localhost,Type=Broker");
		BrokerViewMBean mBean = (BrokerViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(connection, name, BrokerViewMBean.class, true);
		// System.out.println(mBean.getBrokerName());

		Map<String, Long> countMap = new HashMap<String, Long>();
		for (ObjectName queueName : mBean.getQueues()) {
			QueueViewMBean queueMBean = (QueueViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, queueName,
							QueueViewMBean.class, true);
			//System.out.println("\n------------------------------\n");

			// 消息队列名称
			//System.out.println("States for queue --- " + queueMBean.getName());

			// 队列中剩余的消息数
			//System.out.println("Size --- " + queueMBean.getQueueSize());

			countMap.put(queueMBean.getName(), queueMBean.getQueueSize());*/
			// 消费者数
			//System.out.println("Number of consumers --- "
			//		+ queueMBean.getConsumerCount());

			// 出队数
			//System.out.println("Number of dequeue ---"
			//		+ queueMBean.getDequeueCount());
//		}
//
        out.write("请求队列:" + requestMessageBrowser.getCount() + "   回调队列:" + callbackMessageBrowser.getCount());
		return null;
	}
	
	public String listByCid() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		List<ClientInfo> clientInfos = clientInfoMng.findAll();
		
		StringBuffer sb = new StringBuffer();
		
		for(ClientInfo clientInfo: clientInfos){
			String cid = clientInfo.getCid();
			sb.append("客户端：" + clientInfo.getName() + " 请求队列:" + requestMessageBrowser.getCount(cid) + "   回调队列:" +  callbackMessageBrowser.getCount(cid));
			sb.append("<br/>");
		}
		
		out.write(sb.toString());
		return null;
	}
	
	public String findByCid() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String cid = request.getParameter("cid").trim();
		out.write("客户端：" + cid+ " 请求队列:" + requestMessageBrowser.getCount(cid) + "   回调队列:" +  callbackMessageBrowser.getCount(cid));
		return null;
	}
	
	public String findByCidAndPrizeid() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String cid = request.getParameter("cid").trim();
		Integer prizeid = Integer.parseInt(request.getParameter("prizeid"));
		
		//out.write("客户端：" + cid+ " 物品ID: " + prizeid + " 请求队列:" + requestMessageBrowser.getCount(cid, prizeid) + "   回调队列:" +  callbackMessageBrowser.getCount(cid, prizeid));
		return null;
	}
	
	public String findDlqSendFailedList() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		List<PrizeLog> list = dlqMessageBrowser.getSendFailedPrizeLogList(query);
		int totalCount = list.size();
		
		JSONObject json = new JSONObject();
		json.put("totalCount", totalCount);
		
		if(limit + start > totalCount)
			limit = totalCount;
		List<PrizeLog> l = list.subList(start, limit);
		
		JSONArray ja = new JSONArray();
		
		for(PrizeLog p : l){
			
			JSONObject jo = new JSONObject();
			jo.put("gid", p.getGid());
			jo.put("cid", clientInfoMng.getClientInfoByCid(p.getCid()).getName());
			jo.put("appid", p.getAppid());
			jo.put("prizeid", p.getPrizeid());
			jo.put("requesttime", DateUtil.format(p.getRequestTime(), DateUtil.DATETIME));
			
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	
	public String resendByGid() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String gid = request.getParameter("gid");
		if(gid!=null && gid.trim().length() > 1){
			dlqMessageBrowser.resend(gid.trim());
			
			out.write("0");
		}else{
			out.write("1");
		}
		
		return null;
	}
	
	public String findDlqCallbackFailedList() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		List<PrizeLog> list = dlqMessageBrowser.getCallbackFailedPrizeLogList(query);
		int totalCount = list.size();
		
		JSONObject json = new JSONObject();
		json.put("totalCount", totalCount);
		
		if(limit + start > totalCount)
			limit = totalCount;
		List<PrizeLog> l = list.subList(start, limit);
		
		JSONArray ja = new JSONArray();
		
		for(PrizeLog p : l){
			
			JSONObject jo = new JSONObject();
			jo.put("gid", p.getGid());
			jo.put("cid", clientInfoMng.getClientInfoByCid(p.getCid()).getName());
			jo.put("appid", p.getAppid());
			jo.put("prizeid", p.getPrizeid());
			jo.put("sendtime", DateUtil.format(p.getSendTime(), DateUtil.DATETIME));
			
			ja.add(jo);
		}
		
		json.put("records", ja);
		out.write(json.toString());
		return null;
	}
	public String recallbackByGid() throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String gid = request.getParameter("gid");
		if(gid!=null && gid.trim().length() > 1){
			dlqMessageBrowser.resend(gid.trim());
			
			out.write("0");
		}else{
			out.write("1");
		}
		
		return null;
	}
}
