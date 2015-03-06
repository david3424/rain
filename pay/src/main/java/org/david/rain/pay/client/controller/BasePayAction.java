package org.david.rain.pay.client.controller;

import org.apache.commons.lang3.StringUtils;
import org.david.rain.pay.client.dao.entity.OpayDic;
import org.david.rain.pay.client.dao.entity.OpayOrder;
import org.david.rain.pay.client.service.ClientService;
import org.david.rain.pay.client.service.PayService;
import org.david.rain.pay.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;

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
    private String applicationCode;
    @Value("#{configProperties['mol.test.payUrl']}")
    protected String payUrl;
    @Value("#{configProperties['mol.version']}")
    private String version;
    @Value("#{configProperties['mol.test.returnUrl']}")
    private String returnUrl;
    @Value("#{configProperties['mol.SecretKey']}")
    protected String secretKey;

    protected String getErrorRedirect(int type) {
        return getErrorRedirect(type, null);
    }

    protected String getErrorRedirect(Integer type, String error) {
//		if(null == type){
//			return errorRedirect;
//		}
//		if(null == type || type.byteValue() != CashConstants.CASH_TYPE_QUICK_INCOME){
//			return errorRedirect;
//		}
//		return quickErrorRedirect;
        error = StringUtils.trimToEmpty(error);
        if (null != type) {
            return "redirect:/cash/fail?type=" + type + "&error=" + error;
        } else {
            return "redirect:/cash/fail?error=" + error;
        }
    }

    protected String getSuccessRedirect(OpayOrder cashOrder, ModelMap modelMap) {
        int type = cashOrder.getType();
        modelMap.put("cashOrder", cashOrder);
        String orderId = cashOrder.getOrderid();
       /* if(null != type && type.byteValue() == CashConstants.CASH_TYPE_QUICK_INCOME){ //直充
            //执行直充
            Integer gameClientId = cashOrder.getGameId();
            ConsumeClient consumeClient = GameInfoSupport.consumeGameMap().get(gameClientId);
            modelMap.put("consumeClientName", consumeClient.getName());
            modelMap.put("yuanbao", cashOrder.getGoodAmount());
//			return quickSuccessRedirect;
        }else if(null != type && type.byteValue() == CashConstants.CASH_TYPE_INCOME){
            modelMap.put("gcoins", cashOrder.getGoodAmount());
//			return successRedirect;
        }*/
        return "redirect:/cash/success?orderId=" + orderId;
//		return successRedirect;
    }


    /**
     * 验证参数完整性
     *
     * @param opayOrder
     * @return
     */
    protected String check(OpayOrder opayOrder) {
        if (opayOrder.getAppid() == null || opayOrder.getAppid() < 1000) {
            return " appid is illegal ";
        }
        if (StringUtils.isEmpty(opayOrder.getIp())) {
            return "request ip is empty ";
        }
        // todo 待补充
        return null;
    }

    protected OpayDic getClientByAppid(Integer appid) {

        return clientService.getClientByAppid(appid);
    }

    protected Map<String, Object> transfer2Map(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("appid", opayOrder.getAppid());
        resultMap.put("channelid", opayOrder.getChannelid());
        resultMap.put("amount", opayOrder.getAmount());
        resultMap.put("currencycode", opayOrder.getCurrencycode());
        resultMap.put("orderid", opayOrder.getOrderid());
        resultMap.put("returnurl", opayOrder.getReturnurl());
        resultMap.put("userid", opayOrder.getUserid());
        resultMap.put("ip", opayOrder.getIp());
        return resultMap;
    }


    protected Map<String, Object> transfer2MolMap(OpayOrder opayOrder) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", applicationCode);
        resultMap.put("referenceId", opayOrder.getOrderid());
        resultMap.put("version", version);
        resultMap.put("channelId", opayOrder.getChannelid());
        resultMap.put("amount", opayOrder.getAmount());
        resultMap.put("currencyCode", opayOrder.getCurrencycode());
        resultMap.put("returnurl", returnUrl);
        resultMap.put("customerId", opayOrder.getUserid());
        return resultMap;
    }


}
