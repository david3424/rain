package org.david.rain.dao;

import org.david.rain.model.OptionLog;

import java.util.List;


public interface OptionLogDao {

	int countUserOptionLog(String beginDate, String endDate, Integer logType,
                           String account, String message);

	List<OptionLog> getUserOptionLogGrid(String beginDate, String endDate,
                                         Integer logType, String account, String message, int from,
                                         int length, String sidx, String sord);

	int insert(String account, String loginIp, String message);

	int insert(String account, String loginIp, String message, String exeScript);

	int insertUserOptionLog(String account, String loginIp, String message);

	int insertUserLoginLog(String account, String loginIp, String message);

}
