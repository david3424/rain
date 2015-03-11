package org.david.rain.pay;

import org.david.rain.pay.client.dao.entity.OpayOrder;
import org.david.rain.pay.client.service.PayService;
import org.david.rain.pay.utils.SignatureUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 2015/3/9.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class PayRequestTest {
public static final String PAYURL = "http://103.23.44.239/mol/payout" ;
public static final String RETURNURL = "http://www.baidu.com" ; //测试用
public static final String PRIVATEKEY = "T0AxQypxcVdJR0Et" ;
public static final String REFERENCEID = "1000-testcode-number4" ;

    @Autowired
    PayService payService;

    private String sign() throws Exception {

        OpayOrder opayOrder = new OpayOrder();
        opayOrder.setAmount(1);
        opayOrder.setApplicationCode(1000);
        opayOrder.setChannelId(1);
        opayOrder.setReturnUrl(RETURNURL);
        opayOrder.setCurrencyCode("THB");
        opayOrder.setReferenceId(REFERENCEID);
        opayOrder.setCustomerId(100001);
        opayOrder.setIp("127.0.0.1");
        Map<String, Object> params_mol = transfer2MolMap(opayOrder);
        String mol_sign = SignatureUtil.signature(params_mol,PRIVATEKEY);
        System.out.println("signature:" + mol_sign);
        return mol_sign;
//        String mol_re = httpUtil.postRequest(PAYURL,params_mol);

    }

    private Map<String, Object> transfer2MolMap(OpayOrder opayOrder) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("applicationCode", opayOrder.getApplicationCode());
        resultMap.put("channelId", opayOrder.getChannelId());
        resultMap.put("amount", opayOrder.getAmount());
        resultMap.put("returnUrl", opayOrder.getReturnUrl());
        resultMap.put("currencyCode", opayOrder.getCurrencyCode());
        resultMap.put("referenceId", opayOrder.getReferenceId());
        resultMap.put("customerId", opayOrder.getCustomerId());
        resultMap.put("ip", opayOrder.getIp());
        return resultMap;
    }

    @Test
    public void testURL() throws Exception {
              String redirect = PAYURL
                + "?applicationCode=" + 1000
                + "&channelId="+1
                + "&amount="+1
                + "&currencyCode=" + "THB"
                + "&referenceId=" + REFERENCEID
                + "&returnUrl=" + RETURNURL
                + "&customerId=" + 100001
                + "&ip=" + "127.0.0.1"
                + "&signature=" + sign();
        System.out.println(redirect);

    }

    @Test
    public void testCount() throws Exception {

        System.out.println(payService.ifFirstOrder("xxx"));
    }

    @Test
    public void testName() throws Exception {
        System.out.println(payService.getApplicationCodeByReferenceId("1000-testcode-number1"));

    }
}
