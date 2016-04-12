package org.david.rain.wmproxy.module.util;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.david.rain.wmeovg.request.alg.Signature;
import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.PrizeSendType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @version 1.0
 * @ClassName SignatureUtil
 * @Description 服务器端数字签名工具类
 * @date 2010-8-5 下午06:00:03
 */
public class SignatureUtil {

    protected static Logger log = LoggerFactory.getLogger(SignatureUtil.class);


    /**
     * 验证签名，判断请求是否合法
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static boolean isPrizeLegal(PrizeLog prize, String sign, String privateKey) throws NoSuchAlgorithmException {
        if (null == prize || null == sign)
            return false;
        return (sign.equals(SignatureUtil.sign(prize, privateKey)));
    }

    /**
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @Description 计算兑换请求签名（客户端、服务器端一致）
     */
    public static String sign(PrizeLog prize, String privateKey) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        sb.append(prize.getGid().trim());
        sb.append(prize.getCid().trim());
        sb.append(prize.getAppid());
        sb.append(prize.getAccount().trim());
        if (prize.getPrizeSendType() != null && prize.getPrizeSendType() == PrizeSendType.ROLENAME)
            sb.append(prize.getRolename());
        else
            sb.append("_").append(prize.getRoleid()).append("_");
        sb.append(prize.getZoneid());
        sb.append(prize.getPrizeid());
        sb.append(prize.getCount());
        sb.append(prize.getPriority());
        sb.append(prize.getCallback().trim());
        sb.append(privateKey.trim());

        log.info("server(" + sb.toString() + ")" + privateKey.trim() + "**");

        return sign(sb.toString());
    }

    /**
     * @param raw
     * @return String
     * @throws NoSuchAlgorithmException
     * @Description 字符串SHA加密
     */
    public static String sign(String raw) throws NoSuchAlgorithmException {

        //BigInteger sha = new BigInteger(Signature.encryptSHA(raw.getBytes()));
        //System.out.println("server+++++++++++++++++++++   " + sha.toString(16));
        //return sha.toString(32);
        return new String(Hex.encodeHex(Signature.encryptSHA(raw.getBytes())));
    }


    /**
     * @param encodeStr
     * @return String
     * @throws Exception
     * @Description 含中文的字符串解密
     */
    public static String decode(String encodeStr) throws Exception {

        return new String(Signature.decryptBASE64(encodeStr), "UTF-8");
    }

    public void signTest() throws NoSuchAlgorithmException {

        PrizeLog prizeLog = new PrizeLog();

        prizeLog.setCid("04");
        prizeLog.setGid("04-20110212-47c7d3de-3667-11e0-8230-e0cb4e3ebbb2");
        prizeLog.setAppid("shop_test");
        prizeLog.setAccount("pwliaom");
        prizeLog.setRolename("cHdfdGVzdA==");
        prizeLog.setZoneid(988);
        prizeLog.setPrizeid(2214);
        prizeLog.setCount(1);
        prizeLog.setCallback("dmlydHVhbF9yZWNvcmQ=");
        prizeLog.setPriority((byte) 1);

		/*prizeLog.setCid("04");
        prizeLog.setGid("04-20110212-cdeeac4a-3666-11e0-8230-e0cb4e3ebbb2");
		prizeLog.setAppid("shop_test");
		prizeLog.setAccount("pwliaom");
		prizeLog.setRolename("cHdfdGVzdA==");
		prizeLog.setZoneid(988);
		prizeLog.setPrizeid(2214);
		prizeLog.setCount(1);
		prizeLog.setCallback("dmlydHVhbF9yZWNvcmQ=");
		prizeLog.setPriority((byte)1);
		*/
        String sign = sign(prizeLog, "WHR2WShCY1RUZFY9");

        System.out.println(sign);

    }
}
