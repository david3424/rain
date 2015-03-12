package org.david.rain.pay.client.controller;

import org.apache.commons.lang3.StringUtils;
import org.david.rain.pay.client.dao.entity.OpayDic;
import org.david.rain.pay.client.dao.entity.OpayOrder;
import org.david.rain.pay.client.service.ClientService;
import org.david.rain.pay.client.service.PayService;
import org.david.rain.pay.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 2015/3/6.
 * 支付基类
 */
public class BasePayAction implements BasePayInterface {

    @Autowired
    protected PayService payService;
    @Autowired
    protected ClientService clientService;
    @Autowired
    protected HttpUtil httpUtil;

    @Value("#{configProperties['mol.applicationCode']}")
    protected String applicationCode;
    @Value("#{configProperties['mol.payUrl']}")
    protected String payUrl;
    @Value("#{configProperties['mol.version']}")
    private String version;
    @Value("#{configProperties['mol.returnUrl']}")
    private String returnUrl;
    @Value("#{configProperties['mol.SecretKey']}")
    protected String secretKey;

    protected String getErrorRedirect(int type) {
        return getErrorRedirect(type, null);
    }

    protected String getErrorRedirect(Integer code, String error) {
            return "redirect:/mol/fail?code=" + code + "&error=" + error;
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
        if (opayOrder.getApplicationCode() == null ) {
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

    protected Map<String, Object> transfer2Map(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", opayOrder.getApplicationCode());
        resultMap.put("channelId", opayOrder.getChannelId());
        resultMap.put("amount", opayOrder.getAmount());
        resultMap.put("currencyCode", opayOrder.getCurrencyCode());
        resultMap.put("referenceId", opayOrder.getReferenceId());
        resultMap.put("returnUrl", opayOrder.getReturnUrl());
        resultMap.put("customerId", opayOrder.getCustomerId());
        resultMap.put("ip", opayOrder.getIp());
        return resultMap;
    }

  protected Map<String, Object> transfer2QueryMap(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", opayOrder.getApplicationCode());
        resultMap.put("referenceId", opayOrder.getReferenceId());
        return resultMap;
    }

  protected Map<String, Object> transfer2MolQueryMap(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", opayOrder.getApplicationCode());
        resultMap.put("referenceId", opayOrder.getReferenceId());
        resultMap.put("version", version);
      return resultMap;
    }


    protected Map<String, Object> transfer2MolMap(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", applicationCode);
        resultMap.put("referenceId", opayOrder.getReferenceId());
        resultMap.put("version", version);
        resultMap.put("channelId", opayOrder.getChannelId());
        resultMap.put("amount", opayOrder.getAmount());
        resultMap.put("currencyCode", opayOrder.getCurrencyCode());
        resultMap.put("returnUrl", MessageFormat.format(returnUrl,opayOrder.getReferenceId()));
        resultMap.put("customerId", opayOrder.getCustomerId());
        return resultMap;
    }


protected Map<String, Object> transferMol2ClientMap(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", applicationCode);
        resultMap.put("referenceId", opayOrder.getReferenceId());
        resultMap.put("version", version);
        resultMap.put("amount", opayOrder.getAmount());
        resultMap.put("currencyCode", opayOrder.getCurrencyCode());
        resultMap.put("paymentId", opayOrder.getPaymentId());
        resultMap.put("paymentStatusCode", opayOrder.getPaymentStatusCode());
        resultMap.put("paymentStatusDate", opayOrder.getPaymentStatusDate());
        resultMap.put("channelId", opayOrder.getCustomerId());
        resultMap.put("customerId", opayOrder.getCustomerId());
        return resultMap;
    }

}
