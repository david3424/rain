package org.david.rain.wmeovg.callback.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @ClassName ICallBack
 * @Description 回调方法接口
 * @date 2010-8-31 上午11:21:33
 */
public interface ICallbackService {
	/**
	 * 
	 * @Description 结果处理
	 * @param gid 流水�?
	 * @param cid 客户端标�?
	 * @param appid 应用标识
	 * @param status AU调用结果
	 * @param count 此次请求完成的个�?
	 * @param callback 回调参数
	 */
	void receive(String gid, String cid, String appid, int status, int count, String callback) throws Exception;
	
	/**
	 * 
	 * @Description servlet初始化方�?
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException;
}
