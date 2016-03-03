package org.david.rain.monitor.monitor.service.data;


import java.util.ArrayList;
import java.util.List;
import static org.david.rain.monitor.monitor.service.data.ExpressionUtil.*;
/**
 * Created by czw on 14-2-25.
 */
public class ExpressionTokenizer {

    /**
     * 本方法采用 5状态自动机 处理 表达式 的tokenizer
     * 状态0：等待任何输入，输入space等空格字符进入这个状态
     * 状态1：标识符
     * 状态2：数字
     * 状态3：符号未结束 > < 后面可能还有个 =
     * 状态4：符号
     *
     * @param expression
     * @return
     */
    List<String> tokenizer(String expression) throws Exception {
        List<String> tokenList = new ArrayList<>();
        char[] chars = expression.toCharArray();
        StringBuilder tokenTemp = new StringBuilder();
        int i = 0;
        int state = 0;
        while (i < chars.length) {

            if (state == 0) {
                tokenTemp = new StringBuilder();//状态为0 的时候需要初始化一下
                if (isZhiMu(chars[i])) {
                    tokenTemp.append(chars[i++]);
                    state = 1;
                    continue;
                } else if (Character.isDigit(chars[i])) {
                    tokenTemp.append(chars[i++]);
                    state = 2;
                    continue;
                } else if (chars[i] == '<' || chars[i] == '>') {
                    tokenTemp.append(chars[i++]);
                    state = 3;
                    continue;
                } else if (isSimpleSymbol(chars[i])) {
                    tokenTemp.append(chars[i++]);
                    state = 4;
                    continue;
                } else if (isSpace(chars[i])) {
                    i++;
                    continue;
                } else {
                    throw new Exception("对不起，不支持字符：" + chars[i]);
                }
            } else if (state == 1) {
                if (isZhiMu(chars[i]) || Character.isDigit(chars[i])) {
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (chars[i] == '<' || chars[i] == '>') {
                    tokenList.add(tokenTemp.toString());
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    state = 3;
                    continue;
                } else if (isSimpleSymbol(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    state = 4;
                    continue;
                } else if (isSpace(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    tokenTemp = new StringBuilder();
                    state = 0;
                    i++;
                    continue;
                } else {
                    throw new Exception("对不起，不支持字符：" + chars[i]);
                }
            } else if (state == 2) {
                if (Character.isDigit(chars[i])) {
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (isZhiMu(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 1;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (chars[i] == '<' || chars[i] == '>') {
                    tokenList.add(tokenTemp.toString());
                    state = 3;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (isSimpleSymbol(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    state = 4;
                    continue;
                } else if (isSpace(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    tokenTemp = new StringBuilder();
                    state = 0;
                } else {
                    throw new Exception("对不起，不支持字符：" + chars[i]);
                }
            } else if (state == 3) {
                if (chars[i] == '=') {
                    tokenTemp.append(chars[i++]);
                    state = 4;
                    continue;
                } else if (isZhiMu(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 1;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (Character.isDigit(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 2;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (isSimpleSymbol(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 4;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (isSpace(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 0;
                    i++;
                    tokenTemp = new StringBuilder();
                    continue;
                } else {
                    throw new Exception("对不起，不支持字符：" + chars[i]);
                }
            } else if (state == 4) {
                if (isZhiMu(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 1;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (Character.isDigit(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 2;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (chars[i] == '<' || chars[i] == '>') {
                    tokenList.add(tokenTemp.toString());
                    state = 3;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (isSimpleSymbol(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 4;
                    tokenTemp = new StringBuilder();
                    tokenTemp.append(chars[i++]);
                    continue;
                } else if (isSpace(chars[i])) {
                    tokenList.add(tokenTemp.toString());
                    state = 0;
                    tokenTemp = new StringBuilder();
                    i++;
                    continue;
                } else {
                    throw new Exception("对不起，不支持字符：" + chars[i]);
                }
            }
        }
        if (tokenTemp.length() > 0) {
            tokenList.add(tokenTemp.toString());
        }
        return tokenList;
    }

    public static boolean isZhiMu(char i) {
        return (i >= 65 && i <= 90) || (i >= 97 && i <= 122);
    }

    public static void main(String[] args) {
        ExpressionTokenizer expressionTokenizer = new ExpressionTokenizer();
        try {
            List<String> tokens = expressionTokenizer.tokenizer(" 3a * 2b + 3bc <= 4");
            System.out.print(tokens);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
