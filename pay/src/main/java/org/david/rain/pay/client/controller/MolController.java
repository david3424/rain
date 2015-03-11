package org.david.rain.pay.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.david.rain.pay.client.dao.entity.OpayDic;
import org.david.rain.pay.client.dao.entity.OpayOrder;
import org.david.rain.pay.utils.DateUtils;
import org.david.rain.pay.utils.IpUtils;
import org.david.rain.pay.utils.SignatureUtil;
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
    public String query(OpayOrder opayOrder, String signature, HttpServletRequest request, Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer applicationCode = opayOrder.getApplicationCode();
        String checkMsg = check(opayOrder);
        if (StringUtils.isNotEmpty(checkMsg)) { //统一下参数有误接口
            return getErrorRedirect(1001, "checkMsg");//参数有误
        }
        OpayDic dsysDic = getClientByAppid(applicationCode);
        if (dsysDic == null) {
            return getErrorRedirect(1002, "invalid applicationCode ");//applicationCode无效
        }
        String clientIp = IpUtils.getRealIp(request);
        if (!StringUtils.contains(dsysDic.getIp(), clientIp)) {
            return getErrorRedirect(4001, "Unauthorized Server IP Address ");//ip不合法
        }
        if (dsysDic.getStatus() == -1) {
            return getErrorRedirect(4002, "Service is in maintenance。");
        }
        String privatekey = dsysDic.getPrivatekey().trim();
        Map<String, Object> params_sign = transfer2Map(opayOrder);
        String local_sign = SignatureUtil.signature(params_sign, privatekey);
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            return getErrorRedirect(4003, "Invalid Signature. ");//签名有误
        }
        opayOrder.setCreatetime(DateUtils.getCurrentFormatDateTime());
        if (!payService.ifFirstOrder(opayOrder.getReferenceId())) {
            return getErrorRedirect(4004, "duplicatied orderid ");
        }
        opayOrder.setVersion("v1.0");
        opayOrder.setType(0);
        opayOrder.setStatus(0);
        int saveOrder_r = payService.saveOrder(opayOrder);
        if (saveOrder_r != 1) {
            return getErrorRedirect(5001, "System Error2.");//系统保存数据有误
        }
        //进入支付流程
        Map<String, Object> params_mol = transfer2MolMap(opayOrder);
        String mol_sign = SignatureUtil.signature(params_mol, secretKey);
        params_mol.put("signature", mol_sign);
        String mol_re = httpUtil.postRequest(payUrl, params_mol);
        LOG.info("result of mol request :{}", mol_re);
        Map map;
        try {
            map = objectMapper.readValue(mol_re, Map.class);
        } catch (IOException e) {
            LOG.error("objectMapper error :{}", e.getMessage());
            e.printStackTrace();
            return getErrorRedirect(5001, "System Error1.");
        }
        if (null != map.get("message")) {
            return getErrorRedirect(503, (String) map.get("message"));
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
            return getErrorRedirect(5001, "System Error3.");
        }
        return "redirect:" + paymentUrl;
    }


    @RequestMapping(value = "/returned")
    public String returned(OpayOrder opayOrder, String signature) {
        /*if(null == opayOrder.getReferenceId()){
            return getErrorRedirect(5001, "System Error4.");
        }
        Map<String, Object> params_client = transferMol2ClientMap(opayOrder);
        String callback_sign = SignatureUtil.signature(params_client, secretKey);
        if(!StringUtils.equals(callback_sign,signature)){
            return getErrorRedirect(5001, "System Error5.");
        }*/
        LOG.info("opayOrder of returned :{},signature of returned {}", opayOrder, signature);
        OpayOrder opayOrder_local = payService.getOpayOrderByOrderId(opayOrder.getReferenceId());
        LOG.info("returned url in db is :{}", opayOrder_local.getReturnUrl());
        return "redirect:" + opayOrder_local.getReturnUrl();
    }


    @RequestMapping(value = "/callback")
    public String callback(OpayOrder opayOrder, String signature) {
        if (null == opayOrder.getReferenceId()) {
            return getErrorRedirect(5001, "System Error6.");
        }
        LOG.info("opayorder of callback is {}", opayOrder);
        Map<String, Object> params_client = transferMol2ClientMap(opayOrder);
        String callback_sign = SignatureUtil.signature(params_client, secretKey);
        if (!StringUtils.equals(callback_sign, signature)) {
            LOG.error("callback_sign is :{},but signature from mol is {}", callback_sign, signature);
            return getErrorRedirect(5001, "System Error7.");
        }
        payService.updateOrder(opayOrder, "callback");
        String callbackUrl = payService.getApplicationCodeByReferenceId(opayOrder.getReferenceId());
        LOG.info("callbackurl in db is {}", callbackUrl);
        return "redirect:" + callbackUrl;//得把参数带过去
    }


    @RequestMapping(value = "/query")
    @ResponseBody
    public Map query(OpayOrder opayOrder, String signature) {

        Map<String, Object> query_re = new HashMap<>();
        String checkMsg = checkQuery(opayOrder);
        if (StringUtils.isNotEmpty(checkMsg)) { //统一下参数有误接口
            query_re.put("status", 1001);
            query_re.put("message", "checkMsg");
            return query_re;
        }
        OpayDic dsysDic = getClientByAppid(opayOrder.getApplicationCode());
        if (dsysDic == null) {
            query_re.put("status", 1002);
            query_re.put("message", "invalid applicationCode ");
            return query_re;
        }

        String privatekey = dsysDic.getPrivatekey().trim();
        Map<String, Object> params_sign = transfer2QueryMap(opayOrder);
        String local_sign = SignatureUtil.signature(params_sign, privatekey);
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            query_re.put("status", 4003);//签名有误
            query_re.put("message", "Invalid Signature. ");
            return query_re;
        }

        OpayOrder oPayOrder_client = payService.getOpayOrderByOrderId(opayOrder.getReferenceId());
        if (null == oPayOrder_client) {
            query_re.put("status", 1003);//refrenceId 有误
            query_re.put("message", "Invalid referenceId. ");
            return query_re;
        }
        switch (opayOrder.getStatus()) {
            case 0:
                query_re.put("status", 1004);//正在处理
                query_re.put("message", "in process. ");
                break;
            case 1:
                query_re.put("status", 200);//成功
                query_re.put("message", "successful ");
                query_re.put("detail",oPayOrder_client);
                break;
            case 3:
                query_re.put("status", 201);
                query_re.put("message", "failed");
                query_re.put("detail",oPayOrder_client);
                break;
            case 2:
                //去查询
                Map<String, Object> params_mol = transfer2MolQueryMap(opayOrder);
                String mol_sign = SignatureUtil.signature(params_mol, secretKey);
                params_mol.put("signature", mol_sign);
                String mol_re = httpUtil.getRequest(payUrl, params_mol);
                LOG.info("result of mol request :{}", mol_re);
                Map map ;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    map = objectMapper.readValue(mol_re, Map.class);
                    if (null != map.get("message")) {
                        query_re.put("status", 503);//MOL错误
                        query_re.put("message", map.get("message"));
                    }else{
                        opayOrder.setAmount((Integer) map.get("amount"));
                        String pamentStatus = (String) map.get("paymentStatusCode");
                        opayOrder.setPaymentStatusCode(pamentStatus);
                        opayOrder.setPaymentStatusDate((String) map.get("paymentStatusDate"));
                        if(StringUtils.equals(pamentStatus,"00")){
                            opayOrder.setStatus(1);
                            query_re.put("status", 200);
                            query_re.put("message", "successful ");
                            query_re.put("detail",opayOrder);
                        }else{
                            opayOrder.setStatus(3);
                            query_re.put("status", 201);
                            query_re.put("message", "failed");
                            query_re.put("detail",opayOrder);
                        }
                        payService.updateOrder(opayOrder, "callback");
                    }
                } catch (IOException e) {
                    LOG.error("objectMapper error :{}", e.getMessage());
                    e.printStackTrace();
                    query_re.put("status", 5001);
                    query_re.put("message", "System Error1. ");
                }
                break;
        }
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

}
