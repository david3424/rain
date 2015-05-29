package org.david.rain.games.pay.client.dao.entity;

import org.david.rain.games.pay.utils.entity.HdTable;

import java.io.Serializable;

/**
 * Created by david on 2015/3/6.
 * 订单类
 */
@HdTable("o_pay_order")
public class OpayOrder implements Serializable {


    private Integer id;
    private String applicationCode;
    private String channelId;
    private Integer amount;
    private String currencyCode;
    private String version;//暂时不用传 默认1.0
    private String referenceId;
    private String paymentId;
    private String paymentUrl;
    private String returnUrl;
    private String paymentStatusCode;
    private String customerId; //muticard里替换为BIN paymethod
    private Integer type;// 0 mol 1 multicard
    private String createtime;
    private String paymentStatusDate;
    private String ip;
    private Integer status;//默认是0


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentStatusCode() {
        return paymentStatusCode;
    }

    public void setPaymentStatusCode(String paymentStatusCode) {
        this.paymentStatusCode = paymentStatusCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getPaymentStatusDate() {
        return paymentStatusDate;
    }

    public void setPaymentStatusDate(String paymentStatusDate) {
        this.paymentStatusDate = paymentStatusDate;
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

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "OpayOrder{" +
                "id=" + id +
                ", applicationCode=" + applicationCode +
                ", channelId=" + channelId +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", version='" + version + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", paymentUrl='" + paymentUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", paymentStatusCode='" + paymentStatusCode + '\'' +
                ", customerId=" + customerId +
                ", type=" + type +
                ", createtime='" + createtime + '\'' +
                ", paymentStatusDate='" + paymentStatusDate + '\'' +
                ", ip='" + ip + '\'' +
                ", status=" + status +
                '}';
    }
}
