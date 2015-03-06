package org.david.rain.pay.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.david.rain.pay.client.dao.entity.OpayDic;
import org.david.rain.pay.client.dao.entity.OpayOrder;
import org.david.rain.pay.utils.IpUtils;
import org.david.rain.pay.utils.SignatureUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
public class MolController extends BasePayAction{


    /**
     * 进入支付
     */
    @RequestMapping(value = "payout", method = RequestMethod.POST)
    public String query(OpayOrder opayOrder, String signature,HttpServletRequest request,Model model) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer appid = opayOrder.getAppid();
        String checkMsg = check(opayOrder);
        if (StringUtils.isNotEmpty(checkMsg)) { //统一下参数有误接口
            model.addAttribute("status", 10001); //参数有误
            model.addAttribute("message", checkMsg);
            return getErrorRedirect(1);
        }
        OpayDic dsysDic = getClientByAppid(appid);
        if (dsysDic == null) {
            model.addAttribute("status", 10002); //appid无效
            model.addAttribute("message", "invalid appid ");
            return getErrorRedirect(1);
        }
        String clientIp = IpUtils.getRealIp(request);
        if (!StringUtils.contains(dsysDic.getIp(), clientIp)) {
            model.addAttribute("status", 40001); //ip不合法
            model.addAttribute("message", "Unauthorized Server IP Address ");
            return objectMapper.writeValueAsString(model);
        }
        if (dsysDic.getStatus() == -1) {
            model.addAttribute("status", 40002); //禁止访问
            model.addAttribute("message", "Service is in maintenance。");
            return getErrorRedirect(1);
        }
        String privatekey =  dsysDic.getPrivatekey().trim();
        Map<String, Object> params_sign = transfer2Map(opayOrder);
        String local_sign = SignatureUtil.signature(params_sign,privatekey);
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            model.addAttribute("status", 40003); //签名有误
            model.addAttribute("message", "Invalid Signature. ");
            return getErrorRedirect(1);
        }

        //进入支付流程

        Map<String, Object> params_mol = transfer2MolMap(opayOrder);
        String mol_sign = SignatureUtil.signature(params_mol,privatekey);
        params_mol.put("signature",mol_sign);
        String mol_re = httpUtil.postRequest(payUrl,params_mol);
        Map map = objectMapper.readValue(mol_re, Map.class);


        return objectMapper.writeValueAsString(model);
    }



}
