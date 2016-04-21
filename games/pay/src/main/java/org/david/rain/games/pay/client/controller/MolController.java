package org.david.rain.games.pay.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.david.rain.games.pay.client.dao.entity.OpayDTO;
import org.david.rain.games.pay.client.dao.entity.OpayDic;
import org.david.rain.games.pay.client.dao.entity.OpayOrder;
import org.david.rain.games.pay.client.exceptions.UtilsException;
import org.david.rain.games.pay.utils.Bean2MapUtils;
import org.david.rain.games.pay.utils.IpUtils;
import org.david.rain.games.pay.client.dao.entity.OpayQuery;
import org.david.rain.games.pay.utils.DateUtils;
import org.david.rain.games.pay.utils.SignatureUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * <p/>
 * List page     : GET /client/
 * List top100   : GET /client/list
 * Create page   : GET /client/create
 * Create action : POST /client/create
 * update page   : GET /client/update{id}
 * update action : POST /client/update
 * Delete action : get delete/{id}
 */
@Controller
@RequestMapping(value = "/mol")
public class MolController extends BasePayAction {

    public static final Logger LOG = LoggerFactory.getLogger(MolController.class);

    /**
     * 进入支付
     */
    @RequestMapping(value = "payout")
    public String payout(OpayOrder opayOrder, String signature, Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        String checkMsg = check(opayOrder);
        if (StringUtils.isNotEmpty(checkMsg)) { //统一下参数有误接口
            LOG.error("invalid params:{}", checkMsg);
            return getErrorRedirect(10001, "checkMsg");//参数有误
        }
        String applicationCode = opayOrder.getApplicationCode();
        OpayDic dsysDic = getClientByAppid(applicationCode);
        if (dsysDic == null) {
            LOG.error("invalid applicationCode:{}", applicationCode);
            return getErrorRedirect(10002, "invalid applicationCode ");//applicationCode无效
        }
        if (dsysDic.getStatus() == -1) {
            LOG.error("Service appid: {} is in maintenance。", dsysDic.getAppid());
            return getErrorRedirect(10004, "Service is in maintenance。");
        }
        String privatekey = dsysDic.getPrivatekey().trim();
        Map<String, Object> params_sign = new HashMap<>();
        try {
            Bean2MapUtils.bean2map(opayOrder, params_sign);
        } catch (UtilsException e) {
            return getErrorRedirect(10007, "System Error1.");
        }
        String local_sign = SignatureUtil.signature(params_sign, privatekey, "md5");
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            LOG.error("Invalid Signature. local:{},but signature:{}", local_sign, signature);
            return getErrorRedirect(10005, "Invalid Signature. ");//签名有误
        }
        opayOrder.setCreatetime(DateUtils.getCurrentFormatDateTime());
        if (!payService.ifFirstOrder(opayOrder.getReferenceId())) {
            LOG.error("duplicatied referenceId :{}", opayOrder.getReferenceId());
            return getErrorRedirect(10006, "duplicatied referenceId ");
        }
        opayOrder.setVersion("v1.0");
        opayOrder.setType(PayType.MOL.getValue());
        opayOrder.setStatus(0);
        int saveOrder_r = payService.saveOrder(opayOrder);
        if (saveOrder_r != 1) {
            LOG.error("System Error2. saveOrder error:{}", opayOrder);
            return getErrorRedirect(10007, "System Error2.");//系统保存数据有误
        }
        //进入支付流程
        Map<String, Object> params_mol = new HashMap<>();
        try {
            Bean2MapUtils.bean2map(opayOrder, params_mol);
        } catch (UtilsException e) {
            return getErrorRedirect(10007, "System Error1.");
        }
        String mol_sign = SignatureUtil.signature(params_mol, properties.getProperty("mol.SecretKey"), "md5");
        params_mol.put("signature", mol_sign);
        String mol_re = httpUtil.postRequest(properties.getProperty("mol.payUrl"), params_mol);
        LOG.info("referenceId:[{}],result of mol request :{}", opayOrder.getReferenceId(), mol_re);
        Map map;
        try {
            map = objectMapper.readValue(mol_re, Map.class);
        } catch (IOException e) {
            LOG.error("System Error1. objectMapper error :{}", e.getMessage());
            e.printStackTrace();
            return getErrorRedirect(10007, "System Error1.");
        }
        if (null != map.get("message")) {
            LOG.error("returned message error :{}", map.get("message"));
            return getErrorRedirect(10008, (String) map.get("message"));
        }
        String paymentId = (String) map.get("paymentId");
        String paymentUrl = (String) map.get("paymentUrl");
        LOG.info("returned payment url is :{}", paymentUrl);

        //保存支付信息
        opayOrder.setPaymentId(paymentId);
        opayOrder.setStatus(2);//支付中
//        opayOrder.setPaymenturl(paymentUrl);
        int updateOrder_r = payService.updateOrder(opayOrder, "request");
        if (updateOrder_r <= 0) {
            LOG.error("System Error3 . updateOrder error : {}", updateOrder_r);
            return getErrorRedirect(10007, "System Error3.");
        }
        return "redirect:" + paymentUrl;
    }


    @RequestMapping(value = "/returned")
    public String returned(String referenceId) {
        OpayOrder opayOrder_local = payService.getOpayOrderByOrderId(referenceId);
        if (null == opayOrder_local) {
            LOG.error("System Error6. returned referenceId error: {}", referenceId);
            return getErrorRedirect(10007, "System Error6.");
        }
        LOG.info("returned referenceId: {} to url :{}", referenceId, opayOrder_local.getReturnUrl());
        return "redirect:" + opayOrder_local.getReturnUrl();
    }


    @RequestMapping(value = "/callback")
    public String callback(OpayDTO opayDTO, String signature) {
        if (null == opayDTO.getReferenceId()) {
            LOG.error("System Error6. callback referenceId error: {}", opayDTO.getReferenceId());
            return getErrorRedirect(10007, "System Error6.");
        }

        OpayOrder opayOrder = transferDTO2Order(opayDTO);
        LOG.info("opayorder of callback is {}", opayOrder);
        Map<String, Object> params_client = new HashMap<>();

        try {
            Bean2MapUtils.bean2map(opayOrder, params_client);
        } catch (UtilsException e) {
            return getErrorRedirect(10007, "System Error1.");
        }
        String callback_sign = SignatureUtil.signature(params_client, properties.getProperty("mol.SecretKey"), "md5");
        if (!StringUtils.equals(callback_sign, signature)) {
            LOG.error("callback_sign is :{},but signature from mol is {}", callback_sign, signature);
            return getErrorRedirect(10007, "System Error7.");
        }
        switch (opayOrder.getPaymentStatusCode()) {
            case "00":
                opayOrder.setStatus(1);
                break;
            case "01":
                opayOrder.setStatus(2);
                break;
            case "02":
                opayOrder.setStatus(3);
                break; //failed as expired
            case "99":
                opayOrder.setStatus(3);
                break; //Payment for the given transaction failed
            default:
                opayOrder.setStatus(3);
        }
        opayOrder.setPaymentStatusDate(payService.transUTC2Date(opayOrder.getPaymentStatusDate()));
        payService.updateOrder(opayOrder, "callback");
        OpayDic dsysDic = getClientByAppid(opayOrder.getApplicationCode());
        if (dsysDic == null) {
            LOG.error("callback invalid applicationCode:{}", opayOrder.getApplicationCode());
            return getErrorRedirect(10002, "invalid applicationCode ");//applicationCode无效
        }
        String callbackUrl = dsysDic.getCallbackurl();
        LOG.debug("callbackurl in db is {}", callbackUrl);

        //进入支付流程
        Map<String, Object> params_callback = new HashMap<>();

        try {
            Bean2MapUtils.bean2map(opayOrder, params_callback);
        } catch (UtilsException e) {
            return getErrorRedirect(10007, "System Error1.");
        }
        String callback_sign_client = SignatureUtil.signature(params_callback, dsysDic.getPrivatekey(), "md5");
        params_callback.put("signature", callback_sign_client);
        LOG.debug("params of callback is :{}", params_callback);
        String callback_re = httpUtil.postRequest(callbackUrl, params_callback);
        LOG.debug("result of mol-callback [ReferenceId:{}] request :{}", opayOrder.getReferenceId(), callback_re);
        return getErrorRedirect(200, "callback successful");
    }


    @RequestMapping(value = "/query")
    @ResponseBody
    public Map query(OpayOrder opayOrder, String signature) {

        Map<String, Object> query_re = new HashMap<>();
        String checkMsg = checkQuery(opayOrder);
        if (StringUtils.isNotEmpty(checkMsg)) { //统一下参数有误接口
            query_re.put("status", 10001);
            query_re.put("message", "checkMsg");
            LOG.error("query[ReferenceId:{}] error:{}", opayOrder.getReferenceId(), query_re);
            return query_re;
        }
        OpayDic dsysDic = getClientByAppid(opayOrder.getApplicationCode());
        if (dsysDic == null) {
            query_re.put("status", 10002);
            query_re.put("message", "invalid applicationCode ");
            LOG.error("query[ReferenceId:{}] error:{}", opayOrder.getReferenceId(), query_re);
            return query_re;
        }

        String privatekey = dsysDic.getPrivatekey().trim();
        Map<String, Object> params_sign = transfer2QueryMap(opayOrder);
        String local_sign = SignatureUtil.signature(params_sign, privatekey, "md5");
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            query_re.put("status", 10005);//签名有误
            query_re.put("message", "Invalid Signature. ");
            LOG.error("query[ReferenceId:{}] error:{}", opayOrder.getReferenceId(), query_re);
            return query_re;
        }

        OpayOrder oPayOrder_client = payService.getOpayOrderByOrderId(opayOrder.getReferenceId());
        if (null == oPayOrder_client) {
            query_re.put("status", 10009);//refrenceId 有误
            query_re.put("message", "Invalid referenceId. ");
            LOG.error("query[ReferenceId:{}] error:{}", opayOrder.getReferenceId(), query_re);
            return query_re;
        }
        switch (oPayOrder_client.getStatus()) {
            case 0:
                query_re.put("status", 200);//正在处理
                query_re.put("message", "in  processing");
                oPayOrder_client.setStatus(2);
                query_re.put("detail", oPayOrder_client);
                break;
            case 1:
                query_re.put("status", 200);//成功
                query_re.put("message", "successful ");
                query_re.put("detail", oPayOrder_client);
                break;
            case 3:
                query_re.put("status", 200);
                query_re.put("message", "failed");
                query_re.put("detail", oPayOrder_client);
                break;
            case 2:
                //去查询
                opayOrder.setApplicationCode(properties.getProperty("mol.applicationCode"));
                Map<String, Object> params_mol = new HashMap<>();
                try {
                    Bean2MapUtils.bean2map(opayOrder, params_mol);
                } catch (UtilsException e) {
                    query_re.put("status", 10007);
                    query_re.put("message", "System Error1. ");
                    break;
                }
                String mol_sign = SignatureUtil.signature(params_mol, properties.getProperty("mol.SecretKey"), "md5");
                params_mol.put("signature", mol_sign);
                String mol_re = httpUtil.getRequest(properties.getProperty("mol.payUrl"), params_mol);
                LOG.info("result of[ReferenceId:{}] mol query :{}", opayOrder.getReferenceId(), mol_re);
                if (null == mol_re) {
                    LOG.error("network error:mol_re is null ");
                    query_re.put("status", 200);//网络异常
                    query_re.put("message", "network error");
                    return query_re;
                }
                Map map;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    map = objectMapper.readValue(mol_re, Map.class);
                    if (null != map.get("message")) {
                        query_re.put("status", 10008);//MOL错误
                        query_re.put("message", map.get("message"));
                    } else {
                        opayOrder.setAmount((Integer) map.get("amount"));
                        String pamentStatus = (String) map.get("paymentStatusCode");
                        opayOrder.setPaymentStatusCode(pamentStatus);
                        opayOrder.setPaymentStatusDate((String) map.get("paymentStatusDate"));
                        opayOrder.setCurrencyCode((String) map.get("currencyCode"));//加入币种，否则会被覆盖为null
                        if (StringUtils.equals(pamentStatus, "00")) {
                            opayOrder.setStatus(1);
                            query_re.put("status", 200);
                            query_re.put("message", "successful");
                        } else if (StringUtils.equals(pamentStatus, "01")) {
                            query_re.put("status", 200);
                            query_re.put("message", "in middle of processing");
                            opayOrder.setStatus(2);
                        } else {
                            opayOrder.setStatus(3);
                            query_re.put("status", 200);
                            query_re.put("message", "failed");
                        }
                        opayOrder.setPaymentStatusDate(payService.transUTC2Date(opayOrder.getPaymentStatusDate()));
                        query_re.put("detail", opayOrder);
                        payService.updateOrder(opayOrder, "callback");
                    }
                } catch (IOException e) {
                    LOG.error("objectMapper[ReferenceId:{}] error :{}", opayOrder.getReferenceId(), e.getMessage());
                    e.printStackTrace();
                    query_re.put("status", 10007);
                    query_re.put("message", "System Error1. ");
                }
                break;
        }
        LOG.info("query[ReferenceId:{}] result:{}", opayOrder.getReferenceId(), query_re);
        return query_re;
    }


    @RequestMapping(value = "/fail")
    public String fail(String code, String error, ModelMap modelMap) {
        if (StringUtils.isNotBlank(error)) {
            modelMap.put("error", error);
        }
        if (StringUtils.isNotBlank(code)) {
            modelMap.put("code", code);
        }
        return "pay/error";
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public Map search(OpayQuery opayQuery, String applicationCode, String signature, HttpServletRequest request) {

        LOG.info("[1] request payouturl is {}", opayQuery);

        if ((opayQuery.getBegintime() != null && null == opayQuery.getEndtime()) || (null == opayQuery.getBegintime() && opayQuery.getEndtime() != null)) {
            return getMapRedirect(10001, "checkMsg");//日期必须成对出现
        }
        OpayDic dsysDic = getClientByAppid(applicationCode);
        if (dsysDic == null) {
            LOG.error("invalid applicationCode:{}", applicationCode);
            return getMapRedirect(10002, "invalid applicationCode ");//applicationCode无效
        }
        String clientIp = IpUtils.getRealIp(request);
        if (!StringUtils.contains(dsysDic.getIp(), clientIp)) {
            LOG.error("Unauthorized Server IP Address:[{}]", clientIp);
            return getMapRedirect(10003, "Unauthorized Server IP Address ");//ip不合法
        }
        String privatekey = dsysDic.getPrivatekey().trim();
        Map<String, Object> params_sign = new HashMap<>();
        try {
            Bean2MapUtils.bean2map(opayQuery, params_sign);
        } catch (UtilsException e) {
            return getMapRedirect(10007, "System Error1.");
        }
        params_sign.put("applicationCode", applicationCode);
        String local_sign = SignatureUtil.signature(params_sign, privatekey, "md5");
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            LOG.error("Invalid Signature. local:{},but signature:{}", local_sign, signature);
            return getMapRedirect(10005, "Invalid Signature. ");//签名有误
        }
        List queryList = payService.queryList(opayQuery);
        Map<String, Object> re_map = getMapRedirect(200, "successful");
        re_map.put("result", queryList);
        return re_map;
    }

}
