package com.wanmei.games.client.service;

import com.wanmei.games.client.dao.entity.OpayQuery;
import org.apache.commons.lang3.StringUtils;
import com.wanmei.games.client.dao.Idao;
import com.wanmei.games.client.dao.entity.OpayOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * User: david
 * Date: 14-7-4
 * Time: 下午5:22
 */

@Service
public class PayService {

    public static final String PAY_TABLE = "o_pay_order";
    @Autowired
    private Idao wdao;

    public static final Logger LOG = LoggerFactory.getLogger(PayService.class);


    public int saveOrder(OpayOrder opayOrder) {

        try {
            return wdao.insert(opayOrder);
        } catch (SQLException e) {
            LOG.error("insert order[ opayOrder:{}]  failed :{}", opayOrder, e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public boolean ifFirstOrder(String orderid) {
        try {
            long re = wdao.queryScalar("select count(1) from " + PAY_TABLE + " where referenceId = ? ", orderid);
            return re == 0;
        } catch (SQLException e) {
            LOG.error("queryOrderNum [orderid:{}]failed :{}", orderid, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int updateOrder(OpayOrder opayOrder, String type) {
        try {
            if (StringUtils.equals(type, "callback")) {
                return wdao.update("update " + PAY_TABLE + " set amount = ?,currencyCode = ?, status = ? , paymentStatusCode = ?, paymentStatusDate = ? where referenceId = ? ", opayOrder.getAmount(), opayOrder.getCurrencyCode(), opayOrder.getStatus(), opayOrder.getPaymentStatusCode(), opayOrder.getPaymentStatusDate(), opayOrder.getReferenceId());
            } else {
                return wdao.update("update " + PAY_TABLE + " set status = ? , paymentId = ? where referenceId = ? ", opayOrder.getStatus(), opayOrder.getPaymentId(), opayOrder.getReferenceId());
            }
        } catch (SQLException e) {
            LOG.error("updateOrder[ opayOrder:{}] , type[] === failed :{}", opayOrder, type, e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public OpayOrder getOpayOrderByOrderId(String referenceId) {
        try {
            return wdao.queryObject(OpayOrder.class, "select * from " + PAY_TABLE + " where referenceId = ? ", referenceId);
        } catch (SQLException e) {
            LOG.error("getOpayOrderByOrderId[ referenceId:{}] === failed :{}", referenceId, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String getApplicationCodeByReferenceId(String referenceId) {
        String query = "select  b.callbackUrl from " + PAY_TABLE + " a ,o_pay_dic b where a.applicationCode = b.appid and a.referenceId = ? ";
        try {
            LOG.info("refeid:{}", referenceId);
            return wdao.queryScalar(query, referenceId);
        } catch (SQLException e) {
            LOG.error("getApplicationCodeByReferenceId[ referenceId:{}] === failed :{}", referenceId, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String transUTC2Date(String paymentStatusDate) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return dfs.format(df.parse(paymentStatusDate));
        } catch (ParseException e) {
            e.printStackTrace();
            LOG.error("transUTC2Date error:{}", e.getMessage());
            return paymentStatusDate;
        }

    }
    public String transUnix2Date(String paymentStatusDate, String formats) {

        Long timestamp = Long.parseLong(paymentStatusDate)*1000;
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
        return date;

    }

    public List<OpayOrder> queryList(OpayQuery opayQuery) {
        LOG.info("query params :{}", opayQuery);
        StringBuilder sb = new StringBuilder();
        List<Object> arryList = new ArrayList<>();
        sb.append(" select createtime,referenceId,paymentId,customerId,amount,channelId,ip,status from  ").append(PAY_TABLE).append(" where 1=1 and ");
        if (null != opayQuery.getReferenceId()) {
            sb.append(" referenceId =  ? and ");
            arryList.add(opayQuery.getReferenceId());
        }
        if (null != opayQuery.getCustomerId()) {
            sb.append(" customerId =  ?  and ");
            arryList.add(opayQuery.getCustomerId());
        }
        if (null != opayQuery.getPaymentId()) {
            sb.append(" paymentId =  ?  and ");
            arryList.add(opayQuery.getPaymentId());
        }
        if (null != opayQuery.getBegintime() && null != opayQuery.getEndtime()) {
            sb.append(" createtime >=  ? and createtime <= ? and ");
            arryList.add(opayQuery.getBegintime());
            arryList.add(opayQuery.getEndtime());
        }
        if(arryList.size()<=0){//必须要有参数
            return null;
        }
        sb.append("1=1");
        LOG.info("sb of querylist :{}", sb.toString());

        try {
            return wdao.queryObjects(OpayOrder.class, sb.toString(), arryList.toArray());
        } catch (SQLException e) {
            LOG.error("query list exception {} ", e.getMessage());
            e.printStackTrace();
            return  null;
        }
    }
}
