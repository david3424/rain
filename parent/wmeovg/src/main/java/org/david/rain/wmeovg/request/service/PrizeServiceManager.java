package org.david.rain.wmeovg.request.service;


/**
 * @ClassName PrizeServiceManager
 * @Description 兑换服务管理
 * @version 1.0
 * @date 2010-8-13 下午03:09:07
 */
public class PrizeServiceManager {

	public static IPrizeService prizeService;
	
	static{
		if(null == prizeService){
			prizeService = new PrizeServiceImpl();
		}	
	}
}
