package org.david.rain.web.service.hdinterface.entity;


import org.david.rain.common.entity.HdTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 * 玩家获取的Z点积分明细
 */
@HdTable("hd_webmall_interface_score_log")
public class ScoreInfoBean implements Serializable{
    private Integer id;
    private String username;
    private Integer score;
    private String hdname;
    private String tablename;
    private Long orderid;
    private Integer type;
    private String source;  //z点积分来源
    private String ip;
    private String createdate;
    private String createtime;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getHdname() {
        return hdname;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
