package com.wanmei.service.sendcheck;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-4-18
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionSendBean {
    private Integer pid;
    private String tableName;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
