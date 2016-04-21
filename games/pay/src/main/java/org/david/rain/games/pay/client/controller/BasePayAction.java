package org.david.rain.games.pay.client.controller;

import org.apache.commons.lang3.StringUtils;
import org.david.rain.games.pay.client.dao.entity.OpayDTO;
import org.david.rain.games.pay.client.dao.entity.OpayDic;
import org.david.rain.games.pay.client.dao.entity.OpayOrder;
import org.david.rain.games.pay.client.service.ClientService;
import org.david.rain.games.pay.client.service.PayService;
import org.david.rain.games.pay.utils.HttpUtil;
import org.david.rain.games.pay.utils.PropertiesUtil;
import org.david.rain.games.pay.utils.constant.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by david on 2015/3/6.
 * 支付基类
 */
public abstract class BasePayAction implements BasePayInterface {

    @Autowired
    protected PayService payService;
    @Autowired
    protected ClientService clientService;
    @Autowired
    protected HttpUtil httpUtil;

    public static final Properties properties = PropertiesUtil.getProperties(SystemConstants.PROPERTIES_PAY_PATH);


    protected String getErrorRedirect(Integer code, String error) {
        return "redirect:/mol/fail?code=" + code + "&error=" + error;
    }


    protected Map<String, Object> getMapRedirect(Integer code, String checkMsg) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", code);
        map.put("message", checkMsg);
        return map;
    }


    /**
     * 验证参数完整性
     *
     * @param opayOrder
     * @return
     */
    protected String check(OpayOrder opayOrder) {
        if (opayOrder.getApplicationCode() == null) {
            return " appid is illegal ";
        }
        if (StringUtils.isEmpty(opayOrder.getIp())) {
            return "request ip is empty ";
        }
        // todo 待补充
        return null;
    }

    /**
     * 验证参数完整性
     *
     * @param opayOrder
     * @return
     */
    protected String checkQuery(OpayOrder opayOrder) {
        if (opayOrder.getApplicationCode() == null) {
            return " appid is illegal ";
        }
        if (StringUtils.isEmpty(opayOrder.getReferenceId())) {
            return "referenceId is empty ";
        }
        // todo 待补充
        return null;
    }

    protected OpayDic getClientByAppid(String appid) {

        return clientService.getClientByAppid(appid);
    }


    protected Map<String, Object> transfer2QueryMap(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", opayOrder.getApplicationCode());
        resultMap.put("referenceId", opayOrder.getReferenceId());
        return resultMap;
    }

    protected OpayOrder transferDTO2Order(OpayDTO opayDTO) {
        OpayOrder opayOrder = new OpayOrder();
        if (null != opayDTO.getApplicationCode()) {
            opayOrder.setApplicationCode(opayDTO.getApplicationCode());
        }
        if (null != opayDTO.getMerchantId()) {
            opayOrder.setApplicationCode(opayDTO.getMerchantId());
        }
        if (null != opayDTO.getReferenceId()) {
            opayOrder.setReferenceId(opayDTO.getReferenceId());
        }
        if (null != opayDTO.getVersion()) {
            opayOrder.setVersion(opayDTO.getVersion());
        }
        if (null != opayDTO.getAmount()) {
            opayOrder.setAmount(opayDTO.getAmount());
        }
        if (null != opayDTO.getCurrencyCode()) {
            opayOrder.setCurrencyCode(opayDTO.getCurrencyCode());
        }
        if (null != opayDTO.getCurrency()) {
            opayOrder.setCurrencyCode(opayDTO.getCurrency());
        }
        if (null != opayDTO.getPaymentId()) {
            opayOrder.setPaymentId(opayDTO.getPaymentId());
        }
        if (null != opayDTO.getPaymentStatusCode()) {
            opayOrder.setPaymentStatusCode(opayDTO.getPaymentStatusCode());
        }
        if (null != opayDTO.getChannelId()) {
            opayOrder.setChannelId(opayDTO.getChannelId());
        }
        if (null != opayDTO.getPaymentMethod()) {
            opayOrder.setChannelId(opayDTO.getPaymentMethod());
        }
        if (null != opayDTO.getCustomerId()) {
            opayOrder.setCustomerId(opayDTO.getCustomerId());
        }
        if (null != opayDTO.getPaymentStatusDate()) {
            opayOrder.setPaymentStatusDate(opayDTO.getPaymentStatusDate());
        }
        if (null != opayDTO.getPaymentTimestamp()) {
            opayOrder.setPaymentStatusDate(opayDTO.getPaymentTimestamp());
        }
        return opayOrder;
    }
}

enum PayType {
    MOL("Mol", 1), Multi("Multicard", 2), Maxis("Maxis", 3);
    // 成员变量
    private String name;
    private int value;

    // 构造方法
    PayType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    //覆盖方法
    @Override
    public String toString() {
        return this.value + "_" + this.name;
    }


    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int index) {
        this.value = index;
    }
}
