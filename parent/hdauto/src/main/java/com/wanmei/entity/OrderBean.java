package com.david.web.pppppp.entity;

/**
 * davidxu
 */
@HdTable("hd_sendprize_user")
public class OrderBean extends IdEntity {

    private String username;
    private String managername;
    private String actname;
    private String createtime;
    private String gamename;
    private String description;
    private String serverlist;
    private String prizelist;
    private String ordertype;
    private String tablename;
    private String baktablename;
    private Integer totalnum;
    private Integer type;
    private Integer status;


    public String getBaktablename() {
        return baktablename;
    }

    public void setBaktablename(String baktablename) {
        this.baktablename = baktablename;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServerlist() {
        return serverlist;
    }

    public void setServerlist(String serverlist) {
        this.serverlist = serverlist;
    }

    public String getPrizelist() {
        return prizelist;
    }

    public void setPrizelist(String prizelist) {
        this.prizelist = prizelist;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}