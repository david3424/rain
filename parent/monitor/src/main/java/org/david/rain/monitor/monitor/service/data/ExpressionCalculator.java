package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.lang.Tuple;

import java.util.*;
import static org.david.rain.monitor.monitor.service.data.ExpressionUtil.*;

/**
 * Created by czw on 14-2-24.
 */
public class ExpressionCalculator {

    /**
     * 把计算表达式装换成逆波兰式
     *
     * @param expression
     * @return
     * @throws Exception
     */
    public LinkedList<String> toRPN(List<String> expression) throws Exception {
        LinkedList<String> s1 = new LinkedList<>();
        LinkedList<String> s2 = new LinkedList<>();
        //s2.push("#");//先放入一个优先级最低的，这样循环结束就不用一个个吧s2里面一个个压到s1中

        for (String token : expression) {
            if (isNumber(token)) {
                s1.push(token);
            } else if (token.equals("(")) {
                s2.push(token);
            } else if (token.equals(")")) {
                boolean isExist = false;
                while (!s2.isEmpty()) {
                    String calcToken = s2.pop();
                    if (calcToken.equals("(")) {
                        isExist = true;
                        break;
                    }
                    s1.push(calcToken);
                }
                if (!isExist) {
                    throw new Exception("expression wrong, i can not change it to rpn(ni bo lan)");
                }
            } else if (isCalcSymbol(token)) {
                if (s2.isEmpty()) {
                    s2.push(token);
                } else {
                    String s2TopToken = s2.peek();
                    if (s2TopToken.equals("(")) {
                        s2.push(token);
                    } else if (PRIORITY_MAP.get(s2TopToken) <= PRIORITY_MAP.get(token)) {
                        s2.push(token);
                    } else {
                        while (!s2.isEmpty()
                                && !s2.peek().equals("(")
                                && !(PRIORITY_MAP.get(s2.peek()) <= PRIORITY_MAP.get(token))) {
                            s1.push(s2.pop());
                        }
                        s2.push(token);
                    }
                }

            } else { // 如果是标识符，这就和数字是一样的，直接压入s1
                s1.push(token);
            }
        }
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
        return s1;
    }

    /**
     * 使用得到的逆波兰表达式计算表达式的值
     *
     * @param rpn
     * @return
     */
    public Long caclFromRPN(LinkedList<String> rpn) throws Exception {
        LinkedList<Long> result = new LinkedList<>();
        while (!rpn.isEmpty()) {
            String token = rpn.removeLast();
            if (isNumber(token)) {
                result.push(Long.parseLong(token));
            } else if (isCalcSymbol(token)) {
                Long leftValue = result.pop();
                Long rightValue = result.pop();
                if (leftValue == null || rightValue == null) {
                    throw new Exception("the expression is not right ,so i can not calculate it ");
                }
                long r = calc(leftValue, rightValue, token);
                result.push(r);
            } else {
                throw new Exception("sorry, you expression is wrong!!!!");
            }
        }
        if (result.size() > 1)
            throw new Exception("sorry, you expression is wrong!!!!");
        return result.pop();
    }

    public static long calc(long l, long r, String operation) {
        if (operation.equals("*"))
            return l * r;
        else if (operation.equals("/"))
            return l / r;
        else if (operation.equals("+"))
            return l + r;
        else if (operation.equals("-"))
            return l - r;
        else {
            throw new UnsupportedOperationException("sorry, we do not support operation :" + operation);
        }
    }

    /**
     * 把 3a + 2b 转化成 (3*a) + (2*b)
     *
     * @param tokens
     * @return
     */
    public static List<String> addMultiply(List<String> tokens) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (isNumber(tokens.get(i))) {
                if (i < tokens.size() - 1) {
                    if (isIdentifier(tokens.get(i + 1))) {
                        result.add("(");
                        result.add(tokens.get(i));
                        result.add("*");
                        result.add(tokens.get(i + 1));
                        result.add(")");
                        i++;
                        continue;
                    }
                }
            }
            result.add(tokens.get(i));
        }
        return result;
    }


    public LinkedList<String> changeIdentifyToValue(LinkedList<String> rpn, Map<String, Long> valueMap) throws Exception {
        LinkedList<String> result = new LinkedList<>();
        for (String token : rpn) {
            if (isIdentifier(token)) {
                Long value = valueMap.get(token);
                if (value == null) {
                    throw new Exception("sorry, no value for :" + token + " can find");
                }
                result.add(String.valueOf(valueMap.get(token)));
            } else
                result.add(token);
        }
        return result;
    }


    public static void main(String[] args) {
        ExpressionTokenizer expressionTokenizer = new ExpressionTokenizer();
        ExpressionCalculator calculator = new ExpressionCalculator();
        try {
            List<String> tokens = expressionTokenizer.tokenizer("3a*(2b+3)+3<=4");
            System.out.println(tokens);
            Tuple<List<String>, List<String>> twoExpression = ExpressionCheckUtil.cutToTwo(tokens);
            System.out.println(twoExpression.l);
            List<String> finalE = addMultiply(twoExpression.l);
            System.out.println(finalE);
            LinkedList<String> rpn = calculator.toRPN(finalE);
            System.out.println(rpn);
            Map<String, Long> values = new HashMap<>();
            values.put("a", 3L);
            values.put("b", 2L);
            LinkedList<String> valueRpn = calculator.changeIdentifyToValue(rpn, values);
            System.out.println("result:" + calculator.caclFromRPN(valueRpn));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
