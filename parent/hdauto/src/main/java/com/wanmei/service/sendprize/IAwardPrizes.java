package com.wanmei.service.sendprize;

import com.wanmei.entity.PrizeTableBean;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gameuser
 * Date: 13-1-4
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public interface IAwardPrizes {
    void execute() throws Exception;

    void updateStatus(int id, int status) throws Exception;

    int updateGIdAndStatus(int id, int status, String gid) throws Exception;

    public void updateRidAndStatus(int id, int status, int rid) throws Exception;

    void addLog(String username, int server, Long roleid, int prize, int status, int pid, int flag, String gid) throws SQLException;

    String getTableName();

    void setTableName(String tableName);

    int getCounts();

    void setCounts(int counts);

    String getBeginDate();

    void setBeginDate(String beginDate);

    String getEndDate();

    void setEndDate(String endDate);

    boolean isDateLimitExisted();

    void setDateLimitExisted(boolean dateLimitExisted);

    boolean isIfMz();

    void setIfMz(boolean ifMz);

    boolean isIfCoupon();

    void setIfCoupon(boolean ifCoupon);

    PrizeTableBean gainPrizeBean(String tbName, int id) throws SQLException;
}
