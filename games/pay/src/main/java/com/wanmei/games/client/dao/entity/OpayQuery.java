package com.wanmei.games.client.dao.entity;

import java.io.Serializable;

/**
 * Created by david on 2015/3/6.
 * 订单类
 */
public class OpayQuery implements Serializable {


    private String referenceId;
    private String paymentId;
    private String customerId;
    private String begintime;
    private String endtime;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "OpayQuery{" +
                "referenceId='" + referenceId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }
}
