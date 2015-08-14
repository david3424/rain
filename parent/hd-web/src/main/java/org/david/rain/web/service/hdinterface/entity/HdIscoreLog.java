package org.david.rain.web.service.hdinterface.entity;


import org.david.rain.common.entity.HdTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 */
@HdTable("hd_iscore_log")
public class HdIscoreLog implements Serializable {


    private Integer id ;
    private Integer score ;
    private Integer status ;
    private Integer type ; //增1减-1
    private String username;
    private String tablename;//表名，区分同一活动的不同时期
    private String hdname;//活动名
    private String createtime;
    private Long orderid;

    public HdIscoreLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public HdIscoreLog(Integer id, Integer score, Integer status, Integer type, String username, String tablename, String hdname, String createtime, Long orderid) {
        this.id = id;
        this.score = score;
        this.status = status;
        this.type = type;
        this.username = username;
        this.tablename = tablename;
        this.hdname = hdname;
        this.createtime = createtime;
        this.orderid = orderid;
    }


    public HdIscoreLog(Integer score, Integer type, String username, String tablename, String hdname, String createtime, Long orderid) {
        this.score = score;
        this.type = type;
        this.username = username;
        this.tablename = tablename;
        this.hdname = hdname;
        this.createtime = createtime;
        this.orderid = orderid;
    }

     public void dump() { dump(System.out); }
	public void dump(java.io.PrintStream ps)
	{
		ps.println( "score = " + score );
		ps.println( "type = " + type );
		ps.println( "username = " + username );
		ps.println( "tablename = " + tablename );
		ps.println( "hdname = " + hdname );
		ps.println( "createtime = " + createtime );
		ps.println( "orderid = " + orderid );
	}



	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("score = ").append(score == null ? "" : score);
		sb.append(",type = ").append(type == null ? "" : type);
		sb.append(",username = ").append(username == null ? "" : username);
		sb.append(",tablename = ").append(tablename == null ? "" : tablename);
		sb.append(",hdname = ").append(hdname == null ? "" : hdname);
		sb.append(",createtime = ").append(createtime == null ? "" : createtime);
		sb.append(",orderid = ").append(orderid == null ? "" : orderid);
		return sb.toString().trim();
	}



}
