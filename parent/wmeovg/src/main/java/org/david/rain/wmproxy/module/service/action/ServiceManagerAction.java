package org.david.rain.wmproxy.module.service.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;


/**
 * JMS服务管理类
 * 
 *
 */
public class ServiceManagerAction  extends BaseAction {

	/**
	 * 开启请求服务
	 * 
	 * @return
	 * @throws IOException
	 */
	public String startRequest() throws IOException{
		
		PrintWriter out = response.getWriter();
		
		try{
			requestContainer.start();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		out.print(getRStatus());
		return null;
	}
	
	/**
	 * 暂停请求服务
	 * 
	 * @return
	 * @throws IOException
	 */
	public String stopRequest() throws IOException{
		
		PrintWriter out = response.getWriter();
		
		try{
			requestContainer.stop();
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		out.print(getRStatus());
		
		return null;
	}
	
	/**
	 * 读取请求服务状态
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getRequestStatus() throws IOException {
		
		PrintWriter out = response.getWriter();
		int result = -1;
		
		try{
			result = getRStatus();
			
		}catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		
		out.print(result);
		
		return null;
	}
	
	public int getRStatus(){
		
		int result = -1;
		
		if(requestContainer.isRunning())
			result = 0; // 正常运行
		else if(requestContainer.isActive())
			result = 1; // 暂停状态
		else
			result = 2; // down
		
		return result;
	}
	
	/**
	 * 开启回调服务
	 * 
	 * @return
	 * @throws IOException
	 */
	public String startCallback() throws IOException{
		
		PrintWriter out = response.getWriter();
		
		try{
			callbackContainer.start();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		out.print(getCStatus());
		return null;
	}
	
	/**
	 * 暂停回调服务
	 * 
	 * @return
	 * @throws IOException
	 */
	public String stopCallback() throws IOException{
		
		PrintWriter out = response.getWriter();
		
		try{
			callbackContainer.stop();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		out.print(getCStatus());
		
		return null;
	}
	
	/**
	 * 读取回调服务状态
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getCallbackStatus() throws IOException {
		
		PrintWriter out = response.getWriter();
		int result = -1;
		
		try{
			
			result = getCStatus();
			
		}catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		
		out.print(result);
		
		return null;
	}
	
	public int getCStatus(){
		
		int result = -1;
		
		if(callbackContainer.isRunning())
			result = 0; // 正常运行
		else if(callbackContainer.isActive())
			result = 1; // 暂停状态
		else
			result = 2; // down
		
		return result;
	}
	
	/**
	 * 开启goods服务
	 * 
	 * @return
	 * @throws IOException
	 */
	public String startGoods() throws IOException{
		
		PrintWriter out = response.getWriter();
		
		try{
			goodsContainer.start();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		out.print(getGStatus());
		return null;
	}
	
	/**
	 * 暂停回调服务
	 * 
	 * @return
	 * @throws IOException
	 */
	public String stopGoods() throws IOException{
		
		PrintWriter out = response.getWriter();
		
		try{
			goodsContainer.stop();
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		out.print(getGStatus());
		
		return null;
	}
	
	/**
	 * 读取回调服务状态
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getGoodsStatus() throws IOException {
		
		PrintWriter out = response.getWriter();
		int result = -1;
		
		try{
			
			result = getGStatus();
			
		}catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		
		out.print(result);
		
		return null;
	}
	
	public int getGStatus(){
		
		int result = -1;
		
		if(goodsContainer.isRunning())
			result = 0; // 正常运行
		else if(goodsContainer.isActive())
			result = 1; // 暂停状态
		else
			result = 2; // down
		
		return result;
	}
	@Autowired 
	private DefaultMessageListenerContainer requestContainer;
	@Autowired 
	private DefaultMessageListenerContainer callbackContainer;
	@Autowired 
	private DefaultMessageListenerContainer goodsContainer;
}
