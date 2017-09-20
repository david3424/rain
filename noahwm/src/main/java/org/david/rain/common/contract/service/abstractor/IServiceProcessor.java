package org.david.rain.common.contract.service.abstractor;


public interface IServiceProcessor {
	/**
	 * 所有service都需要实现这个接口
	 * @return
	 */
	String doHandler(String date);
}