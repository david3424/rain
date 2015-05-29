package common;

import java.io.Serializable;

/**
 * Created by IDEA.
 * Date: 13-6-20
 * Time: 下午3:10
 */
public class HdUser implements Serializable {

    private static final Long serialVersionUID = 35412431234L;
    //* 为必填
    private String username; //*
    private String userid;//*
    private Integer server;//*
    private String servername;//*
    private String rolename;//*
    private String roleid;//*
    private String ip;//*
    private String searchstart; //充值记录查询开始时间
    private String searchend; //充值记录查询结束时间
    private Integer type; //用户类型，如vip等 || 激活码 激活类型 || 取卡类型(0=不登录取卡，1=激活码，2=新手卡，3=媒体卡，4=特权卡)不够追加 || 邮件模板tid || 淘宝渠道
    private String fromclient = "0"; //来自客户端=3，ie=1、html=0、mobile=2等
    private Integer gameid;//*
    private String termid;// 建团ID
    private Integer status = 9; //是否审核字段，默认为审核状态9  ||  vip接口查询:  0：获取体验期内的实际级别 -1：获取所有信息
    private String sn; //流水号 || vip查询相关 || 激活码 || 导师号 || 取卡接口卡号 || 淘宝二维码订单号
    private String commodity; //商品名称
    private Integer commodityId; //商品ID，体验包ID，配置文件提供
    private String commodityPrice; //商品价格
    private String factionName; //帮派名
    private String hdid; //活动id 用于统计

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFromclient() {
        return fromclient;
    }

    public void setFromclient(String fromclient) {
        this.fromclient = fromclient;
    }

    public String getSearchstart() {
        return searchstart;
    }

    public void setSearchstart(String searchstart) {
        this.searchstart = searchstart;
    }

    public String getSearchend() {
        return searchend;
    }

    public void setSearchend(String searchend) {
        this.searchend = searchend;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public String getHdid() {
        return hdid;
    }

    public void setHdid(String hdid) {
        this.hdid = hdid;
    }

    @Override
    public String toString() {
        return "HdUser{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", server=" + server +
                ", servername='" + servername + '\'' +
                ", rolename='" + rolename + '\'' +
                ", roleid='" + roleid + '\'' +
                ", ip='" + ip + '\'' +
                ", searchstart='" + searchstart + '\'' +
                ", searchend='" + searchend + '\'' +
                ", type=" + type +
                ", fromclient='" + fromclient + '\'' +
                ", gameid=" + gameid +
                ", termid='" + termid + '\'' +
                ", status=" + status +
                ", sn='" + sn + '\'' +
                ", commodity='" + commodity + '\'' +
                ", commodityId=" + commodityId +
                ", commodityPrice='" + commodityPrice + '\'' +
                ", factionName='" + factionName + '\'' +
                ", hdid='" + hdid + '\'' +
                '}';
    }
}
