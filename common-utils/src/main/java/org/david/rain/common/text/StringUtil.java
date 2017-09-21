package org.david.rain.common.text;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil extends StringUtils {

    public static final String ST_BLANK = "";
    public static final String ST_NULL = "null";
    public static final String ST_COMMA = ",";
    public static final String ST_VERTICAL = "\\|";
    public static final String ST_ENTER = "\n";
    public static final String ST_QUOTE = "'";
    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";
    /**
     * 分号
     */
    public static final String SEMICOLON = ";";

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    /**
     * 空格字符串
     */
    public static final String SPACE = " ";

    /**
     * 字符串省略号
     */
    public static final String ELLIPSIS = "...";

    public static final String DEFAULT_MIN = "0";

    public static final String DEFAULT_MAX = "999999999999";

    public static String wrapObjectMinValue(Object obj) {
        return wrapObjectVal(obj, DEFAULT_MIN);
    }

    public static String wrapObjectMaxValue(Object obj) {
        return wrapObjectVal(obj, DEFAULT_MAX);
    }

    /**
     * @param obj
     * @return
     */
    private static String wrapObjectVal(Object obj, String defaultVal) {
        if (obj == null) {
            return defaultVal;
        }
        return obj.toString();
    }

    public static boolean isEmpty(String... strs) {
        for(int i = 0; i < strs.length; ++i) {
            if (isEmpty(strs[i])) {
                return true;
            }
        }
        return false;
    }

    public static String isEmptyValue(String st1, String st2) {
        if (st1 != null) {
            return st1;
        } else if (st2 != null) {
            return st2;
        }
        return st1;
    }

    public static String replaceNull(String st, String replacement) {
        if (replacement != null) {
            if (st == null) {
                return replacement;
            } else {
                return st;
            }
        }
        return st;
    }

    public static String replaceNullWithBlank(String st) {
        return replaceNull(st, ST_BLANK);
    }

    public static String formatString(String prefix, String st, String surfix) {
        if ((st != null) && (prefix != null) && (surfix != null)) {
            return prefix + st + surfix;
        }
        return st;
    }

    public static boolean isBlank(String str) {
        return !StringUtils.isNotBlank(str);
    }

    public static boolean isNotBlank(String str) {
        if (str == null) {
            return false;
        } else if ("".equalsIgnoreCase(str.trim())) {
            return false;
        } else if ("null".equalsIgnoreCase(str.trim())) {
            return false;
        }
        return true;

    }

    /**
     * @param characterCode character Code
     * @param byteSize      Byte Size
     * @method:judgeByte
     * @param:value Character to be judged
     * @return:boolean Whether in order to encode
     * @exception:none
     * @description: The byte is judged 判断是多单字节还是多字节
     */
    public final static boolean judgeByte(String value, String characterCode, int byteSize) throws Exception {
        /*-----Begin define-----*/
        boolean Return = false;
        byte[] temp_t = null;
        /*-----End define-------*/
		/*-----Begin Operate----*/
        try {
            if (isNotBlank(characterCode)) {
                temp_t = value.getBytes(characterCode);
            } else {
                temp_t = value.getBytes();
            }
            if (byteSize < 1) {
                byteSize = 1;
            }
            if (temp_t != null && temp_t.length == byteSize) {
                Return = true;
            }
        } catch (Exception ex) {

            Return = false;
        }
        return Return;
		/*-----End Operate------*/
    }

    /**
     * <p>
     * 在字符串后用省略号来缩略字符串。如将“经历了五月的淡季，市场和消费者在平静中迎来夏日的炎热”缩略成“经历了五月的淡季...”。
     * </p>
     *
     * @param str      要缩略的字符串，可以为空。
     * @param maxWidth 已缩略的字符串的最大的字节长度（含3个省略号的长度），最小为4。
     * @return 已缩略的字符串, 当输入空字符串时返回<code>null</code>。
     * @throws IllegalArgumentException 当maxWidth小于4时抛出
     *                                  <p>
     *                                  <pre>
     *                                                                        Example：
     *                                                                        StringUtil.abbreviateByByte(null, *)      = &quot;&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;&quot;, 4)        = &quot;&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;, 40) = &quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;, 30) = &quot;一二三四五六七八九十1234567..&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;, 13) = &quot;一二三四五六七八九十...&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;, 9) = &quot;一二三四五六...&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;, 4) = &quot;一...&quot;
     *                                                                        StringUtil.abbreviateByByte(&quot;一二三四五六七八九十1234567890ABCDEFGHIJ&quot;, 3) = IllegalArgumentException
     *                                                                   </pre>
     */
    public final static String abbreviateByByte(final String str, final int maxWidth) {
        if (isBlank(str)) {
            return "";
        } else {
            if (maxWidth < 4) {
                throw new IllegalArgumentException("Minimum abbreviation width is 4");
            }
            if (str.getBytes().length <= maxWidth) {
                // 长度合适
                return str;
            } else {
                // 长度不合适
                StringBuffer sb = new StringBuffer();
                int iTemp = 0;
                int i;
                int iEnd = 0;
                for (i = 0; i < str.length(); i++) {
                    try {
                        if (judgeByte(str.substring(i, i + 1), "GBK", 2)) {
                            if (iTemp + 2 <= maxWidth) {
                                iTemp = iTemp + 2;
                            } else {
                                break;
                            }
                            if (iTemp + StringUtil.ELLIPSIS.length() <= maxWidth) {
                                iEnd = i + 1;
                            }
                        } else {
                            if (iTemp + 1 <= maxWidth) {
                                iTemp = iTemp + 1;
                            } else {
                                break;
                            }
                            if (iTemp + StringUtil.ELLIPSIS.length() <= maxWidth) {
                                iEnd = i + 1;
                            }
                        }
                    } catch (Exception e) {
                        return "";
                    }
                }
                // 刚好
                if ((i) == str.length()) {
                    return str;
                } else
                // 超过
                {
                    sb.append(str.substring(0, iEnd));
                    sb.append(StringUtil.ELLIPSIS);
                    return sb.toString();
                }
            }
        }
    }

    public static String formatCardNum(String cardNum) {
        StringBuffer sb = new StringBuffer();
        if (!isBlank(cardNum) && cardNum.length() == 18) {
            sb.append(cardNum);
            sb.replace(1, 17, "****************");
        }
        return sb.toString();
    }

    public static String formatMobile(String phoneNumber) {
        StringBuffer sb = new StringBuffer();
        if (!isBlank(phoneNumber) && phoneNumber.length() >= 7) {
            sb.append(phoneNumber);
            sb.replace(3, 7, "****");
        }
        return sb.toString();
    }

    public static String formatEmail(String email) {
        StringBuffer sb = new StringBuffer();
        if (!isEmpty(email) && email.indexOf("@") != -1) {
            //			sb.append(email);
            //			sb.replace(0, email.indexOf("@"), "******");
            int len = email.indexOf("@");
            if (len > 4) {
                sb.append(email.substring(0, len - 4)).append("****").append(email.substring(len));
            } else {
                sb.append("****").append(email.substring(len));
            }
        }
        return sb.toString();
    }

    public static boolean isMobileNO(String phoneNumber) {
        Pattern p = Pattern.compile("^((0|)(13[0-9])|(15[^4,\\D])|(18[0-9])|(198))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isNumber(String num) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    public static boolean isFellow(String param) {
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{0,}$");
        Matcher m = p.matcher(param);
        return m.matches();
    }

    public static boolean verifyUsername(String param) {
        Pattern p = Pattern.compile("^[A-Za-z0-9_@.-]{0,}$");
        Matcher m = p.matcher(param);
        return m.matches();
    }

    public static String getSecret(byte[] b) {// 得到密文
        String string = "";
        int c = b.length / 6;
        int last = b.length % 6;
        int point = 0;
        int temp = 0;
        for (int i = 0; i < c; i++) {
            for (int j = 6; j > 0; j--) {
                int temp1 = Integer.valueOf(b[point]) - 48;
                int z = 0;
                while (z < j - 1) {
                    temp1 = temp1 * 2;
                    z++;
                }
                point++;
                temp = temp + temp1;
            }
            String string2 = String.valueOf((char) temp);
            string = string + string2;
            temp = 0;
        }
        for (; last > 0; last--) {
            int z = 0;
            int temp1 = Integer.valueOf(b[point]) - 48;
            while (z < last - 1) {
                temp1 = temp1 * 2;
                z++;
            }
            point++;
            temp = temp + temp1;
        }
        String string2 = String.valueOf((char) temp);
        string = string + string2;
        return string;
    }

    public static String strToBinstr(String str) {

        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]);
        }
        return result;
    }

    // 二进制转字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    //字符串转二进制
    public static byte[] hex2byte(String str) {
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 != 0)
            return null;
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getFormatNumBerString(BigDecimal num) {
        DecimalFormat df = new DecimalFormat(",##0.00");
        String result = df.format(num);
        return result;
    }

    public static String getFormatNumBerString2(BigDecimal num) {
        DecimalFormat df = new DecimalFormat(",##0");
        String result = df.format(num);
        return result;
    }

    public static String getFormatNumBerString3(BigDecimal num) {
        DecimalFormat df = new DecimalFormat(",##0.00");
        String result = "";
        if (num.longValue() < 10000) {
            result = df.format(num);
        } else {
            result = num.longValue() / 10000 + "万";
        }
        return result;
    }

    public static String removeTail0(String str) {
        // 如果字符串尾部不为0，返回字符串
        if (!str.substring(str.length() - 1).equals("0")) {
            return str;
        } else {
            // 否则将字符串尾部删除一位再进行递归
            return removeTail0(str.substring(0, str.length() - 1));
        }
    }

    public static String getBankAccount(String bankAccount) {
        if (bankAccount != null && bankAccount.length() > 8) {
            String bankAccount1 = bankAccount.substring(0, 4);
            String bankAccount2 = bankAccount.substring(bankAccount.length() - 4, bankAccount.length());
            bankAccount = bankAccount1 + "********" + bankAccount2;
        }
        return bankAccount;
    }

    public static String getImageExtension(String postfixName) {

        if (!isEmpty(postfixName) && postfixName.indexOf(".") != -1) {
            postfixName = postfixName.substring(postfixName.lastIndexOf("."), postfixName.length());
        }
        if ((!".bmp".equals(postfixName)) || (!".jpg".equals(postfixName)) || (!".gif".equals(postfixName))
                || (!".png".equals(postfixName))) {
            postfixName = ".jpg";
        }

        return postfixName;
    }

    public static String getChar(String str) {
        if (str.length() < 24) {
            for (int i = 0; i < (24 - str.length()); i++) {
                str += " ";
            }
        }
        return str;
    }


    /*
     * 页面汉字乱码转换
     */
    public static String getBytes(String str) throws Exception {
        if (!isEmpty(str)) {
            str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(verifyUsername("11111_1111.11111@1111"));
        System.out.println(getFormatNumBerString(new BigDecimal(1111111111111d).add(new BigDecimal(1111111111111d))));
    }

    /**
     * 截取字符串
     *
     * @param val 要截取的字符串
     * @param flg 0或者1
     * @return
     * @index 分割条件，比如点，以点进行分割
     */
    public static String substringVal(String val, String flg, String index) {
        if (!StringUtil.isEmpty(val)) {
            if ("0".equals(flg)) {
                val = val.substring(0, val.indexOf(index));//截取点前面
            } else if ("1".equals(flg)) {
                val = val.substring(val.indexOf(index) + 1, val.length());//截取点后面
            }
        }
        return val;
    }

    /**
     * 将为null参数转换为""串
     *
     * @param args
     * @return
     */
    public static String getVal(String args) {
        if (args == null) {
            return ST_BLANK;
        }
        return args.trim();
    }

    /**
     * 格式金额格式为 *.** eg:0.10
     *
     * @param amountOfMoney
     * @return
     */
    public static String formatPrice(String amountOfMoney) {
        String amountOfMoneyZx = amountOfMoney;
        String[] strarray = amountOfMoney.split("\\.");
        if (strarray[0].toString().isEmpty()) {
            amountOfMoneyZx = "0" + amountOfMoneyZx;
        }
        if (amountOfMoney.length() != 1) {
            amountOfMoney = amountOfMoney.substring(amountOfMoney.lastIndexOf('.') + 1);
            int count = amountOfMoney.length();
            if (count == 1) {
                amountOfMoneyZx = amountOfMoneyZx + "0";
            }
        }
        return amountOfMoneyZx;
    }

    /**
     * format 银行卡号
     *
     * @param bankCard
     * @return
     */
    public static String formatBankCard(String bankCard) {
        String card = bankCard;
        if (!"".equals(bankCard) && bankCard.length() > 4) {
            StringBuffer bf = new StringBuffer();
            bf.append(bankCard.substring(0, 4));
            String temp = bankCard.substring(4, bankCard.length() - 4);
            bf.append(temp.replaceAll("\\d", "*"));
            bf.append(bankCard.substring(bankCard.length() - 4, bankCard.length()));
            card = bf.toString();
        }
        return card;
    }

    public static int getWordCount(String s) {
        return isEmpty(s) ? 0 : s.getBytes().length;
    }

    /**
     * Generate a special length password string,
     * which contains of 'a'-'z', 'A'-'Z', '0'-'9'
     *
     * @param length
     * @return String
     */
    public static String randomPassword(int length) {
        StringBuffer password = new StringBuffer();
        int index = 0;
        while (index < length) {
            char ascii = (char) Math.floor(Math.random() * 125);
            if ((ascii >= 'a' && ascii <= 'z') || (ascii >= 'A' && ascii <= 'Z') || (ascii >= '0' && ascii <= '9')) {
                password.append(String.valueOf(ascii));
                index++;
            }
        }
        return password.toString();
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password  Password or other credentials to use in authenticating
     *                  this username
     * @param algorithm Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        if (password == null)
            password = "";
        byte[] unencodedPassword = password.getBytes();
        MessageDigest md;
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords
     * as cookies.
     * <p/>
     * This is weak encoding in that anyone can use the decodeString
     * routine to reverse the encoding.
     *
     * @param str
     * @return String
     */
    public static String encodeString(String str) {
        if (str == null)
            str = "";
        return EncodeUtil.encodeBase64(str.getBytes());
    }

    /**
     * Decode a string using Base64 encoding.
     *
     * @param str
     * @return String
     */
    public static String decodeString(String str) {
        if (str == null)
            str = "";
        return new String(EncodeUtil.decodeBase64(str));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getListByToken(String str, String token) {
        if (str == null) {
            return new ArrayList();
        }
        StringTokenizer st = new StringTokenizer(str, token);
        List list = new ArrayList();
        while (st.hasMoreElements()) {
            list.add(st.nextElement().toString().trim());
        }
        return list;
    }

    public static String getFirstUpperCaseVarName(String var) {
        if (var == null || "".equals(var)) {
            return var;
        }
        return var.substring(0, 1).toUpperCase() + var.substring(1, var.length());
    }

    public static String getFirstLowerCaseVarName(String var) {
        if (var == null || "".equals(var)) {
            return var;
        }
        return var.substring(0, 1).toLowerCase() + var.substring(1, var.length());
    }

    public static String removeZeroPrefix(String gmn) {
        if (gmn == null || !gmn.startsWith("0")) {
            return gmn;
        }
        return removeZeroPrefix(gmn.substring(1));
    }

    /**
     * 验证字符串必须由正整数和字母组成
     */
    public static boolean checkNumberAndChar(String str) {
        return str.matches("^[a-zA-Z0-9]*$");
    }

    /**
     * 验证字符串是否超过指定长度
     */
    public static boolean checkLength(String str, int length) {
        if (StringUtils.isNotEmpty(str)) {
            if (getByteLength(str) > length) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取字符串的字节长度
     */
    public static int getByteLength(String source) {
        int len = 0;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            int highByte = c >>> 8;
            len += highByte == 0 ? 1 : 2;
        }
        return len;
    }

    /**
     * 随机生成验证码
     *
     * @param count 验证码位数
     * @return
     */
    public static String getVerifyCode(int count) {
        String sRand = "";
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    /**
     * 判断输入的字符串参数是否为非空
     */
    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    /**
     * 验证邮箱格式
     */
    public static boolean isEmail(String str) {
        return str.matches("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
    }

    /**
     * 订单金额(正整数)格式
     */
    public static boolean isOrderAmount(String str) {
        return str.matches("^[1-9]\\d*$");
    }

    /**
     * 交易密码(数字)格式
     */
    public static boolean isTradePassword(String str) {
        return str.matches("^[0-9]\\d*$");
    }



    /**
     * 将字符串转为BigDecimal
     */
    public static BigDecimal convertStringToBigDecimal(String value) {
        BigDecimal bd = BigDecimal.ZERO;
        if (isEmpty(value)) {
            return bd;
        } else {
            return new BigDecimal(value);
        }
    }

    /**
     * 将字符串转为BigDecimal的字符串，支持科学计数法
     */
    public static String convertStringToBigDecimalPlainStr(String value) {
        BigDecimal bd = BigDecimal.ZERO;
        if (isEmpty(value)) {
            return bd.toString();
        } else {
            return new BigDecimal(value).toPlainString();
        }
    }

    /**
     * 验证手机号码格式
     */
    public static boolean isMobile(String str) {
        return str.matches("^((0|)(13[0-9])|(15[^4,\\D])|(18[0-9])|(198)|(147)|(170))\\d{8}$");
    }

    /**
     * 格式化数字
     */
    public static String decimalFormatAmt(Double amt) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("###,###.00");
        return myformat.format(amt);
    }

    /**
     * 字符编码
     */
    public static String encode(String chinese) {
        return encode(chinese, "UTF-8");
    }

    /**
     * 字符编码
     *
     */
    public static String encode(String chinese, String charset) {
        chinese = chinese == null ? "" : chinese;
        try {
            return URLEncoder.encode(chinese, charset);
        } catch (UnsupportedEncodingException e) {
            return chinese;
        }
    }

    /**
     * 自动生成GUID
     */
    public static String getGUID(String prefix) {
        java.util.UUID uuid = java.util.UUID.randomUUID();
        if (isEmpty(prefix)) {
            return uuid.toString().replaceAll("-", "").toUpperCase();
        } else {
            String UpperUUID = uuid.toString().replaceAll("-", "").toUpperCase();
            return prefix + UpperUUID.substring(0, UpperUUID.length() - prefix.length());
        }
    }


    /**
     * @Name: parseMobile
     * @Description: 手机号中间四位***
     * @Author: zhangjun
     * @Create Date: 2015-2-9下午4:38:22
     * @Parameters: MyStringUtil
     * @Return: String
     */
    public static String parseMobile(String mobile) {
        return mobile.substring(0, mobile.length() - (mobile.substring(3)).length()) + "****" + mobile.substring(7);
    }

    // 不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
    public static boolean equalStr(String numOrStr) {
        boolean flag = true;
        char str = numOrStr.charAt(0);
        for (int i = 0; i < numOrStr.length(); i++) {
            if (str != numOrStr.charAt(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    // 不能是连续的数字--递增（如：123456、12345678）连续数字返回true
    public static boolean isOrderNumeric(String numOrStr) {
        boolean flag = true;// 如果全是连续数字返回true
        boolean isNumeric = true;// 如果全是数字返回true
        for (int i = 0; i < numOrStr.length(); i++) {
            if (!Character.isDigit(numOrStr.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        if (isNumeric) {// 如果全是数字则执行是否连续数字判断
            for (int i = 0; i < numOrStr.length(); i++) {
                if (i > 0) {// 判断如123456
                    int num = Integer.parseInt(numOrStr.charAt(i) + "");
                    int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
                    if (num != num_) {
                        flag = false;
                        break;
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    // 不能是连续的数字--递减（如：987654、876543）连续数字返回true
    public static boolean isOrderNumeric_(String numOrStr) {
        boolean flag = true;// 如果全是连续数字返回true
        boolean isNumeric = true;// 如果全是数字返回true
        for (int i = 0; i < numOrStr.length(); i++) {
            if (!Character.isDigit(numOrStr.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        if (isNumeric) {// 如果全是数字则执行是否连续数字判断
            for (int i = 0; i < numOrStr.length(); i++) {
                if (i > 0) {// 判断如654321
                    int num = Integer.parseInt(numOrStr.charAt(i) + "");
                    int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
                    if (num != num_) {
                        flag = false;
                        break;
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;

    }

    /**
     * 字段位数调整
     *
     */
    public static String getDigitAdjustment(String digital, int num) {
        if (!StringUtil.isEmpty(digital)) {
            BigDecimal bd = new BigDecimal(Double.parseDouble(digital));
            digital = getDigitAdjustment(bd, num);
        }
        return digital;
    }

    /**
     * 字段位数调整
     *
     */
    public static String getDigitAdjustment(BigDecimal digital, int num) {
        if (digital != null) {
            return digital.setScale(num, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
        return null;
    }

    /**
     * 判断币种
     */
    public static String getProductcurrency(String productcurrency) {
        if ("RMB".equals(productcurrency)) {
            productcurrency = "元";
        } else if ("USD".equals(productcurrency)) {
            productcurrency = "美元";
        } else if ("HKD".equals(productcurrency)) {
            productcurrency = "港元";
        } else if ("EUR".equals(productcurrency)) {
            productcurrency = "欧元";
        } else if ("GBP".equals(productcurrency)) {
            productcurrency = "英镑";
        } else if ("JPY".equals(productcurrency)) {
            productcurrency = "日元";
        } else if ("KRW".equals(productcurrency)) {
            productcurrency = "韩元";
        } else if ("CAD".equals(productcurrency)) {
            productcurrency = "加元";
        } else if ("AUD".equals(productcurrency)) {
            productcurrency = "澳元";
        } else if ("CHF".equals(productcurrency)) {
            productcurrency = "瑞郎";
        } else if ("SGD".equals(productcurrency)) {
            productcurrency = "新加坡元";
        }
        return productcurrency;
    }

    /**
     * 格式化百分比数据 1.0变为 1.0% 0.0变为--
     *
     * @param navg
     * @return
     */
    public static String formatNumberPercentage(String navg) {
        try {
            if (Double.parseDouble(navg) != 0) {
                navg = navg + "%";
            } else {
                navg = "--";
            }
        } catch (Exception e) {
            navg = "--";
        }
        return navg;
    }

}
