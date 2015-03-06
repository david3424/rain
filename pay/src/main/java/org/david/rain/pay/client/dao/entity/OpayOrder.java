package org.david.rain.pay.client.dao.entity;

import org.david.rain.pay.utils.entity.HdTable;

import java.io.Serializable;

/**
 * Created by david on 2015/3/6.
 * 订单类
 */
@HdTable("o_pay_order")
public class OpayOrder implements Serializable {


    private Integer id;
    private Integer appid;
    private Integer channelid;
    private Integer amount;
    private String currencycode;
    private String version;
    private String orderid;
    private String paymentid;
    private String paymenturl;
    private String returnurl;
    private Integer userid;
    private Integer type;
    private String createtime;
    private String finishtime;
    private String ip;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymenturl() {
        return paymenturl;
    }

    public void setPaymenturl(String paymenturl) {
        this.paymenturl = paymenturl;
    }

    public String getReturnurl() {
        return returnurl;
    }

    public void setReturnurl(String returnurl) {
        this.returnurl = returnurl;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "OpayOrder{" +
                "id=" + id +
                ", appid=" + appid +
                ", channelid=" + channelid +
                ", amount=" + amount +
                ", currencycode='" + currencycode + '\'' +
                ", version='" + version + '\'' +
                ", orderid='" + orderid + '\'' +
                ", paymentid='" + paymentid + '\'' +
                ", paymenturl='" + paymenturl + '\'' +
                ", returnurl='" + returnurl + '\'' +
                ", userid=" + userid +
                ", type=" + type +
                ", createtime='" + createtime + '\'' +
                ", finishtime='" + finishtime + '\'' +
                ", ip='" + ip + '\'' +
                ", status=" + status +
                '}';
    }
}
