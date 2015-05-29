package com;

import org.david.rain.games.pay.client.dao.entity.OpayOrder;
import org.david.rain.games.pay.client.dao.entity.OpayQuery;
import org.david.rain.games.pay.client.service.PayService;
import org.david.rain.games.pay.utils.SignatureUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by david on 2015/3/9.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class PayRequestTest {
public static final String PAYURL = "http://103.23.44.239/mol/payout" ;
public static final String QUERYURL = "http://103.23.44.239/mol/query" ;
public static final String RETURNURL = "http://www.baidu.com?query=xxx" ; //测试用
public static final String PRIVATEKEY = "T0AxQypxcVdJR0Et" ;
public static final String REFERENCEID = "1000-testcode-number14" ;
public static final String CURRENCYCODE = "0" ;
public static final String CHANNELID = "3" ;
public static final int AMOUNT = 0 ;

    @Autowired
    PayService payService;

    private String sign() throws Exception {

        OpayOrder opayOrder = new OpayOrder();
        opayOrder.setAmount(AMOUNT);
        opayOrder.setApplicationCode("1000");
        opayOrder.setChannelId(CHANNELID);
        opayOrder.setReturnUrl(RETURNURL);
        opayOrder.setCurrencyCode(CURRENCYCODE);
        opayOrder.setReferenceId(REFERENCEID);
        opayOrder.setCustomerId("100001");
        opayOrder.setIp("127.0.0.1");
        Map<String, Object> params_mol = transfer2MolMap(opayOrder);
        String mol_sign = SignatureUtil.signature(params_mol,PRIVATEKEY,"md5");
        System.out.println("signature:" + mol_sign);
        return mol_sign;
//        String mol_re = httpUtil.postRequest(PAYURL,params_mol);

    }
 private String signQuery() throws Exception {

        OpayOrder opayOrder = new OpayOrder();
        opayOrder.setApplicationCode("1000");
        opayOrder.setReferenceId(REFERENCEID);
        Map<String, Object> params_mol = transfer2MolMap(opayOrder);
        String mol_sign = SignatureUtil.signature(params_mol,PRIVATEKEY,"md5");
        System.out.println("signature-query:" + mol_sign);
        return mol_sign;

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
                + "&channelId="+CHANNELID
                + "&amount="+AMOUNT
                + "&currencyCode=" + CURRENCYCODE
                + "&referenceId=" + REFERENCEID
                + "&returnUrl=" + RETURNURL
                + "&customerId=" + "100001"
                + "&ip=" + "127.0.0.1"
                + "&signature=" + sign();
        System.out.println(redirect);
    }

  @Test
    public void testQueryURL() throws Exception {
              String redirect = QUERYURL
                + "?applicationCode=" + 1000
                + "&referenceId=" + REFERENCEID
                + "&signature=" + signQuery();
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

    @Test
    public void testUTC() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(dfs.format(df.parse("2015-03-11T08:08:41Z")));

    }

    @Test
    public void testQueryList() throws Exception {

        OpayQuery opayQuery = new OpayQuery();
        opayQuery.setCustomerId("100001");
        opayQuery.setReferenceId("1000-testcode-number1");
        opayQuery.setBegintime("2014-03-10 11:01:22");
        opayQuery.setEndtime("2016-03-10 11:01:22");
        List<OpayOrder> aa = payService.queryList(opayQuery);
        for(OpayOrder o:aa){
            System.out.println(o);
        }
    }



    @Test
    public void testUnixTime() throws Exception {
        long time = System.currentTimeMillis()/1000;
        System.out.println(time);

    }
}
