package org.david.rain.wmeovg.request.service;


/**
 * @Description 兑换服务管理
 * @version 1.0
 */
public class PrizeServiceManager {

	public static IPrizeService prizeService;
	
	static{
		if(null == prizeService){
			prizeService = new PrizeServiceImpl();
		}	
	}
}
