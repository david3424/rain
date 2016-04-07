package org.david.rain.wmeovg.request.entity;

import org.david.rain.wmeovg.request.entity.base.BasePrizeLog;

import java.util.Date;


public class PrizeLog extends BasePrizeLog {
	private static final long serialVersionUID = 1L;

	public PrizeLog() {
		super();
		initialize();
	}

	public PrizeLog(String gid) {
		super(gid);
		initialize();
	}

	public PrizeLog(String gid, String cid, String appid, Integer zoneid, String account,
			String rolename, Long roleid, Integer prizeid, Integer count, Byte priority,
			String callback, Byte sendStatus,Integer processCount, Byte callbackStatus,
			Date requestTime, Date sendTime, String messageId,
			Byte prizeResendCount, Date callbackTime,
			Integer callbackHttpStatus, Byte callbackCount, String ip, Byte prizeSendType) {
		super(gid, cid, appid, zoneid, account, rolename, roleid, prizeid, count, priority,
				callback, sendStatus, processCount, callbackStatus, requestTime,
				sendTime, messageId, prizeResendCount, callbackTime,
				callbackHttpStatus, callbackCount, ip, prizeSendType);
		initialize();
	}

	@Override
	protected void initialize() {
	}
}
