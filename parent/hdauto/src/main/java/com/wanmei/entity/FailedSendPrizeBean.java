package com.david.web.pppppp.entity;

import java.util.Date;

/**
 * davidxu
 */
@HdTable("kefu_sendprize_failed")
public class FailedSendPrizeBean extends IdEntity{

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

    public FailedSendPrizeBean(String username, String rolename, Integer server, Integer prize) {
        this.username = username;
        this.rolename = rolename;
        this.server = server;
        this.prize = prize;
    }

    public FailedSendPrizeBean(String username, Integer server, String roleid, Integer prize) {
        this.username = username;
        this.roleid = roleid;
        this.server = server;
        this.prize = prize;
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
    private String token;
    private Integer prize;
    private Integer status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FailedSendPrizeBean() {
    }

}