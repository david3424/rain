package org.david.rain.games.pay.client.dao.entity;

import java.io.Serializable;

/**
 * Created by david on 2015/3/6.
 * 传递信息bean
 */
public class OpayDTO implements Serializable {



    private String applicationCode;
    private String channelId;
    private String currencyCode;
    private String version;//暂时不用传 默认1.0
    private String referenceId;
    private String paymentUrl;
    private String returnUrl;
    private String paymentStatusCode;
    private String customerId; //muticard里替换为BIN paymethod
    private String createtime;
    private String paymentStatusDate;
    private String ip;
    private Integer id;
    private Integer amount;
    private String merchantId;
    private String paymentMethod;
    private String paymentId;
    private String currency;
    private String refId;
    private String paymentTimestamp;
    private String status;//muticard返回的是success


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

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public void setPaymentTimestamp(String paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OpayMutiOrder{" +
                "applicationCode='" + applicationCode + '\'' +
                ", channelId='" + channelId + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", version='" + version + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", paymentUrl='" + paymentUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", paymentStatusCode='" + paymentStatusCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", createtime='" + createtime + '\'' +
                ", paymentStatusDate='" + paymentStatusDate + '\'' +
                ", ip='" + ip + '\'' +
                ", id=" + id +
                ", amount=" + amount +
                ", merchantId='" + merchantId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", currency='" + currency + '\'' +
                ", refId='" + refId + '\'' +
                ", paymentTimestamp='" + paymentTimestamp + '\'' +
                ", status=" + status +
                '}';
    }
}
