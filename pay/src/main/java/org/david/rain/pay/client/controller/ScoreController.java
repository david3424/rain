package org.david.rain.pay.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.david.rain.pay.client.dao.entity.DsysDic;
import org.david.rain.pay.client.service.ClientService;
import org.david.rain.pay.utils.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/pay")
public class ScoreController {


    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ClientService clientService;

    /**
     * 进入支付
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public String query(Integer appid, String userip, String signature, Long userid, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> resultdata = new HashMap<>();

        if (userid == null || appid == null || StringUtils.isEmpty(userip) || StringUtils.isEmpty(signature)) { //统一下参数有误接口
            resultdata.put("status", 1001); //参数有误
            resultdata.put("message", "参数有误。"); //参数有误
            return objectMapper.writeValueAsString(resultdata);
        }
        DsysDic dsysDic = clientService.getClientByAppid(appid);
        if (dsysDic == null) {
            resultdata.put("status", 1002); //appid不合法
            resultdata.put("message", "appid不合法。"); //appid不合法
            return objectMapper.writeValueAsString(resultdata);
        }
        if (!StringUtils.contains(dsysDic.getIp(), userip)) {
            resultdata.put("status", 1003); //ip不合法
            resultdata.put("message", "ip不合法。"); //ip不合法
            return objectMapper.writeValueAsString(resultdata);
        }
        if (dsysDic.getStatus() == -1) {
            resultdata.put("status", 1010); //禁止访问
            resultdata.put("message", "禁止访问。"); //禁止访问
            return objectMapper.writeValueAsString(resultdata);
        }
        String privatekey = dsysDic.getPrivatekey();
        String pp = privatekey.trim();
        String local_sign = SignatureUtil.getMd5str(String.valueOf(appid) + userip + userid + pp);
        if (!StringUtils.equalsIgnoreCase(local_sign, signature)) {
            resultdata.put("status", 1004); //签名有误
            resultdata.put("message", "签名有误。"); //签名有误
            return objectMapper.writeValueAsString(resultdata);
        }
        resultdata.put("status", 1);
        return objectMapper.writeValueAsString(resultdata);
    }


}
