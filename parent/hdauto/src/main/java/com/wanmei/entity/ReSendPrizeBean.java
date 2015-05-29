package com.david.web.wanmei.entity;

import com.david.web.wanmei.service.ServiceException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * davidxu
 */
@HdTable("kefu_sendprize_temp")
public class ReSendPrizeBean extends IdEntity {

    public static final int EXCEL_ROW_NUM = 4;

    public static final Map<Integer, String> IDX_COLUMN_MAP = new HashMap<Integer, String>(4);

    public static final String NUMBER_COLUMN = "int";
    public static final String STRING_COLUMN = "String";


    public static final String USERNAME_COLUMN = "username";
    public static final String ROLEID_COLUMN = "roleid";
    public static final String SERVER_COLUMN = "server";
    public static final String PRIZE_COLUMN = "prize";

    static {
        IDX_COLUMN_MAP.put(0, USERNAME_COLUMN);
        IDX_COLUMN_MAP.put(1, ROLEID_COLUMN);
        IDX_COLUMN_MAP.put(2, SERVER_COLUMN);
        IDX_COLUMN_MAP.put(3, PRIZE_COLUMN);
    }

    public static final Map<String, String> COLUMN_TYPE_MAP = new HashMap<String, String>(4);

    static {
        COLUMN_TYPE_MAP.put(USERNAME_COLUMN, STRING_COLUMN);
        COLUMN_TYPE_MAP.put(ROLEID_COLUMN, NUMBER_COLUMN);
        COLUMN_TYPE_MAP.put(SERVER_COLUMN, NUMBER_COLUMN);
        COLUMN_TYPE_MAP.put(PRIZE_COLUMN, NUMBER_COLUMN);
    }


    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ReSendPrizeBean(String username, String rolename, Integer server, Integer prize) {
        this.username = username;
        this.rolename = rolename;
        this.server = server;
        this.prize = prize;
    }

    public ReSendPrizeBean(String username, Integer server, String roleid, Integer prize) {
        this.username = username;
        this.roleid = roleid;
        this.server = server;
        this.prize = prize;
    }

    public ReSendPrizeBean(Map<String, Object> params) {
        if (params.get(ROLEID_COLUMN) == null
                || params.get(SERVER_COLUMN) == null
                || params.get(USERNAME_COLUMN) == null
                || params.get(PRIZE_COLUMN) == null) {
            throw new ServiceException("非法数据！");
        }
        this.username = (String) params.get(USERNAME_COLUMN);
        this.roleid = String.valueOf(params.get(ROLEID_COLUMN));
        this.server = ((Long) params.get(SERVER_COLUMN)).intValue();
        this.prize = ((Long) params.get(PRIZE_COLUMN)).intValue();

    }

    public String getIp() {
        return ip;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public void setIp(String ip) {

        this.ip = ip;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    private String rolename;
    private String username;
    private String ip;
    private String gid;
    private Date createdate;
    private Integer server;
    private Integer rid;
    private String roleid;
    private Integer prize;
    private Integer status;

    public ReSendPrizeBean() {
    }

}