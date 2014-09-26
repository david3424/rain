package org.david.rain.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.david.rain.utils.Utils;
import org.david.rain.dao.OptionLogDao;
import org.david.rain.service.GeneralService;

@Scope(value = "singleton")
@Service("generalCollectionService")
public class GeneralServiceImpl implements GeneralService {

	private static final Logger logger = LoggerFactory
			.getLogger(GeneralServiceImpl.class);

	@Autowired
	private HttpServletRequest request;

	@Resource(name = "userOptionLogDao")
	private OptionLogDao userOptionLogDao;

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int insertUserOptionLog(String message) {
		logger.debug("insertUserOptionLog start");

		String account = Utils.getLoginAccount();
		String loginIp = Utils.getIpAddr(request);

		int records = userOptionLogDao.insertUserOptionLog(account, loginIp, message);

		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int insertUserLoginLog(String message) {
		logger.debug("insertUserLoginLog start");

		String account = Utils.getLoginAccount();
		String loginIp = Utils.getIpAddr(request);

		int records = userOptionLogDao.insertUserLoginLog(account, loginIp, message);

		return records;
	}

}
