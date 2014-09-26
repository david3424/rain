package org.david.rain.utils.hession;

public interface IWebService {
	/**
	 * 代理方法
	 * @param loginfo
	 * @param type
	 * @param ip
	 * @param port
	 * @param params
	 * @param objs  ojbs[0]-->url; objs[1]-->method; objs[2]-->class
	 * @return
	 */
	public Object callProxy(LogInfo loginfo, String ip, String port, Object params, Object... objs);
}
