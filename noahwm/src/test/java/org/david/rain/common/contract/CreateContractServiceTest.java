package org.david.rain.common.contract;


import com.ifaclub.common.utils.StringUtil;
import org.david.rain.common.contract.service.CreateContractService;
import com.noah.exception.CommonErrorCode;
import com.noah.exception.ContractException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xdw9486 on 2016/8/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/appli*"})
public class CreateContractServiceTest {

    @Autowired
    CreateContractService createContractService;

    @Test
    public void testCreateContract() throws Exception {


        String datas = "{\n" +
                "    \"ID_Type\": \"1\",\n" +
                "    \"Tx_CustomerName\": \"证件过期二\",\n" +
                "    \"ID_Number\": \"310104198508196594\",\n" +
                "    \"Tx_ProductDate\": \"2016-12-12\",\n" +
                "    \"Tx_LoanAmount\": \"1000000\",\n" +
                "    \"Tx_LoanDate\": \"2016-12-12\",\n" +
                "    \"Tx_EndDate\": \"2016-12-12\",\n" +
                "    \"Tx_ValueDate\": \"2016-12-12\",\n" +
                "    \"Tx_ProductName\": \"2016-12-12\",\n" +
                "    \"Tx_ProductId\": \"2016-12-12\",\n" +
                "    \"Tx_AnnualBenefitRate\": \"2016-12-12\",\n" +
                "    \"Tx_OrderAmountYuan\": \"3,000,000.00\",\n" +
                "    \"Tx_CardNumber\": \"6226625588224466\",\n" +
                "    \"Tx_BankName\": \"中国光大银行\",\n" +
                "    \"sysContractName\": \"160513000019\",\n" +
                "    \"sysTemplateUrl\": \"https://dn-ifa-contract.qbox.me/20160517_e2612997058357709424a9c690d5c1ca.pdf?e=1468483203&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:plpDwYZImM5z0sRyUTNRazGohPQ=\",\n" +
                "    \"sysTemplateName\": \"34661477127088\",\n" +
                "    \"Tx_ContractNumber\": \"160513000019\",\n" +
                "}";


        String JG_data = "{\n" +
//                "    \"Tx_CustomerName\": \"清科创投有限公司\",\n" +
                "    \"Tx_Year\": \"\",\n" +
                "    \"Tx_Month\": \"\",\n" +
                "    \"Tx_Day\": \"\",\n" +
                "    \"JG_Year\": \"2018\",\n" +
                "    \"JG_Month\": \"06\",\n" +
                "    \"JG_Day\": \"30\",\n" +
                "    \"Tx_DayAll\": \"2017年05月31日\",\n" +
                "    \"Cb_LawI\": \"on\",\n" +
                "    \"Tx_CardNumber\": \"6225888818880008\",\n" +
                "    \"Tx_BankName\": \"招商银行\",\n" +
                "    \"Tx_PayAmountYuan\": \"4,005,000.00\",\n" +
                "    \"Tx_PayCapitalYuan\": \"肆佰万零伍仟\",\n" +
                "    \"Tx_PayAmount\": 400.5,\n" +
                "    \"Tx_PayCapital\": \"肆佰点伍\",\n" +
                "    \"Tx_OrderAmount\": 400,\n" +
                "    \"Tx_OrderAmountCapital\": \"肆佰\",\n" +
                "    \"Tx_OrderAmountYuan\": \"4,000,000.00\",\n" +
                "    \"Tx_OrderAmountCapitalYuan\": \"肆佰万\",\n" +
                "    \"Cb_Level1\": \"on\",\n" +
                "    \"Tx_ChargeFee\": 0.5,\n" +
                "    \"Tx_ChargeFeeCapital\": \"点伍\",\n" +
                "    \"Tx_ChargeFeeYuan\": \"5,000.00\",\n" +
                "    \"Tx_ChargeFeeCapitalYuan\": \"伍仟\",\n" +
                "    \"sysReloadTemplate\": false,\n" +
                "    \"JG_MingChen\": \"清科创投有限公司\",\n" +
                "    \"JG_YingYeZhiZhao\": \"FRQT201608167752\",\n" +
                "    \"JG_DaiMa\": \"ZZDM201608176651\",\n" +
                "    \"JG_FaRen\": \"清科创投\",\n" +
                "    \"JG_LianXiRen\": \"清科创投\",\n" +
                "    \"Tx_ContractNumber\": \"170531000020\",\n" +
                "    \"sysTemplateUrl\": \"?e=1504175783&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:P2z-BTmvZCfZyAXKWjgQSDYxeHE=\",\n" +
                "    \"sysTemplateName\": \"机构测试-电子\",\n" +
                "    \"sysContractName\": \"170531000020\"\n" +
                "}";

//        String xx1 = "{\"Tx_Year\":\"2016\",\"Tx_CustomerName\":\"联调测试二\",\"Tx_PayAmountYuan\":\"1,700,000.00\",\"Tx_PayCapital\":\"壹佰柒拾\",\"sysReloadTemplate\":false,\"sysTemplateUrl\":\"https://dn-ifa-dev-contract.qbox.me/20160825_a26fa18eecbd30b5a18265dd8f7a9227.pdf?e=1473148202&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:FQcQs4jHFM9HnmWQS6joKJGNEYI=\",\"Tx_Day\":\"06\",\"ID_TypeName\":\"身份证\",\"Tx_OrderAmountYuan\":\"1,700,000.00\",\"ID_Number\":\"310105198501018898\",\"Tx_Month\":\"09\",\"Tx_BankName\":\"中国光大银行\",\"sysTemplateName\":\"18711472097484\",\"ID_Type\":\"1\",\"Tx_ContractNumber\":\"160906000006\",\"Tx_OrderAmountCapitalYuan\":\"壹佰柒拾万\",\"Tx_PayAmount\":170,\"sysContractName\":\"160906000006\",\"Tx_OrderAmountCapital\":\"壹佰柒拾\",\"Cb_LawI\":\"on\",\"Cb_Level1\":\"on\",\"Tx_OrderAmount\":170,\"Tx_DayAll\":\"2016年09月06日\",\"Tx_CardNumber\":\"6226740369712222\",\"Tx_PayCapitalYuan\":\"壹佰柒拾万\"}";
        String xx = "{\"Tx_Year\":\"2016\",\"Tx_InitialAmount\":100,\"sysReloadTemplate\":false,\"Tx_InitialAmountCapitalYuan\":\"壹佰万\",\"ID_Number\":\"420124197706273526\",\"Tx_BankName\":\"中国建设银行\",\"Tx_InitialAmountCapital\":\"壹佰\",\"Tx_OrderAmountCapitalYuan\":\"壹佰万\",\"sysContractName\":\"160125000017\",\"Cb_LawI\":\"on\",\"Tx_CardNumber\":\"6227003********3590\",\"Tx_DayAll\":\"2016年01月25日\",\"Tx_OrderAmount\":100,\"Tx_InitialAmountYuan\":\"1,000,000.00\",\"Tx_CustomerName\":\"曾金萍\",\"Tx_PayAmountYuan\":\"1,000,000.00\",\"Tx_PayCapital\":\"壹佰\",\"sysTemplateUrl\":\"https://dn-ifa-contract.qbox.me/20151231%2F750145c226eb403c8e6b5574195d055a.pdf?e=1483006412&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:PRp6WvnVJGWJxclr8-yXLWKJoO0=\",\"Tx_Day\":\"25\",\"Tx_OrderAmountYuan\":\"1,000,000.00\",\"ID_TypeName\":\"身份证\",\"Tx_Month\":\"01\",\"sysTemplateName\":\"21071481855884\",\"ID_Type\":\"1\",\"Tx_ContractNumber\":\"160125000017\",\"Tx_PayAmount\":100,\"Tx_OrderAmountCapital\":\"壹佰\",\"Cb_Level1\":\"on\",\"Tx_PayCapitalYuan\":\"壹佰万\"}";
        ContractData contractData;
        try {
//            contractData = JSON.parseObject(xx, ContractData.class);
            contractData = JSON.parseObject(JG_data, ContractData.class);
           /* contractData = new ContractData();
            contractData.setSysTemplateName("18711472097484");
            contractData.setSysTemplateUrl("https://dn-ifa-dev-contract.qbox.me/20160825_a26fa18eecbd30b5a18265dd8f7a9227.pdf?e=1473148202&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:FQcQs4jHFM9HnmWQS6joKJGNEYI=");
            contractData.setSysContractName("160906000006");
            contractData.setTx_ContractNumber("160906000006");*/
        } catch (Exception e) {
            throw new ContractException(CommonErrorCode.PARSING_PARAMS_ERROR);
        }

        if (StringUtil.isEmpty(contractData.getSysTemplateName(), contractData.getSysContractName(), contractData.getSysTemplateUrl(), contractData.getTx_ContractNumber())) {
            throw new ContractException(CommonErrorCode.PARAM_ILLEGAL);
        }

        createContractService.createContract(contractData);

    }


    @Test
    public void testPrevCreateContract() throws Exception {


        String xx = "{\"Tx_Year\":\"2016\",\"sysReloadTemplate\":false,\"ID_Number\":\"310101198508195373\",\"Tx_BankName\":\"中国光大银行\",\"Tx_OrderAmountCapitalYuan\":\"贰佰万\",\"sysContractName\":\"161012000013\",\"Cb_LawI\":\"on\",\"Tx_CardNumber\":\"6226730033774410\",\"Tx_DayAll\":\"2016年10月12日\",\"Tx_OrderAmount\":200,\"Tx_CustomerName\":\"账号新\",\"Tx_PayAmountYuan\":\"2,000,000.00\",\"Tx_PayCapital\":\"贰佰\",\"Tx_Day\":\"12\",\"sysTemplateUrl\":\"https://dn-ifa-dev-contract.qbox.me/20161010_a26fa18eecbd30b5a18265dd8f7a9227.pdf?e=1476357003&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:3Q5bNbg1cfw19SVPJtVj-t0Sqps=\",\"Tx_OrderAmountYuan\":\"2,000,000.00\",\"ID_TypeName\":\"身份证\",\"Tx_Month\":\"10\",\"sysTemplateName\":\"19241476346133\",\"ID_Type\":\"1\",\"Tx_ContractNumber\":\"161012000013\",\"Tx_PayAmount\":200,\"Tx_OrderAmountCapital\":\"贰佰\",\"Cb_Level1\":\"on\",\"Tx_PayCapitalYuan\":\"贰佰万\"}";
        System.out.println(xx);
        ContractData contractData;
        try {
            contractData = JSON.parseObject(xx, ContractData.class);
           /* contractData = new ContractData();
            contractData.setSysTemplateName("18711472097484");
            contractData.setSysTemplateUrl("https://dn-ifa-dev-contract.qbox.me/20160825_a26fa18eecbd30b5a18265dd8f7a9227.pdf?e=1473148202&token=_gsUZbrLzcDO-vHRLojw0holcS0oaICOyrkDiMOh:FQcQs4jHFM9HnmWQS6joKJGNEYI=");
            contractData.setSysContractName("160906000006");
            contractData.setTx_ContractNumber("160906000006");*/
        } catch (Exception e) {
            throw new ContractException(CommonErrorCode.PARSING_PARAMS_ERROR);
        }

        if (StringUtil.isEmpty(contractData.getSysTemplateName(), contractData.getSysContractName(), contractData.getSysTemplateUrl(), contractData.getTx_ContractNumber())) {
            throw new ContractException(CommonErrorCode.PARAM_ILLEGAL);
        }

        createPreviewContractService.createPrevContract(contractData);

    }
}

