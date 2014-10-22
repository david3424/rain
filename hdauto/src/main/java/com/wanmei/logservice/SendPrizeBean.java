package com.wanmei.logservice;

/**
 * Created by czw on 14-2-18.
 */
public class SendPrizeBean {
    private String username;
    private Long userid;
    private Integer server;
    private Long roleid;
    private Integer prize;
    private Integer status;

    private String gid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SendPrizeBean{" +
                "username='" + username + '\'' +
                ", userid=" + userid +
                ", server=" + server +
                ", roleid=" + roleid +
                ", prize=" + prize +
                ", status=" + status +
                ", gid='" + gid + '\'' +
                '}';
    }
}
