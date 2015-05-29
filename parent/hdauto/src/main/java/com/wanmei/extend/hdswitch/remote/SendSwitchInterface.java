package com.david.web.wanmei.extend.hdswitch.remote;

/**
 * Created by czw on 13-12-16.
 */
public interface SendSwitchInterface {
    public String getJobListJson(int pageSize, int pageNo, String tableName) throws Exception;

//    public int closeSendPrize(String prizeTable, String datasource) throws Exception;
//
//    public int openSendPrize(String prizeTable, String datasource) throws Exception;

    public int openSendPrize(String prizeTable) throws Exception;

    public int closeSendPrize(String prizeTable) throws Exception;

    public int closeAll() throws Exception;

    public int openAll() throws Exception;

}
