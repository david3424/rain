package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.lang.Tuple;
import org.david.rain.monitor.monitor.domain.DataCheckSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by czw on 14-2-28.
 */
public class ExpressionCheckUtil {

    static Logger logger = LoggerFactory.getLogger(ExpressionCheckUtil.class);
    static ExpressionCalculator expressionCalculator = new ExpressionCalculator();
    static ExpressionTokenizer expressionTokenizer = new ExpressionTokenizer();

    public static List<String> tokenizer(String expression) throws Exception {
        return expressionTokenizer.tokenizer(expression);
    }

    public static boolean isWright(DataCheckSetting dataCheckSetting, Map<String, Long> valueMap) throws Exception {
        List<String> tokens = expressionTokenizer.tokenizer(dataCheckSetting.getExpression());
        Tuple<List<String>, List<String>> twoExpression = cutToTwo(tokens);
        List<String> left = ExpressionCalculator.addMultiply(twoExpression.l);
        List<String> right = ExpressionCalculator.addMultiply(twoExpression.r);
        Long leftResult = expressionCalculator.caclFromRPN(expressionCalculator.changeIdentifyToValue(expressionCalculator.toRPN(left), valueMap));
        logger.debug(dataCheckSetting.getChName() + " leftResult:" + leftResult);
        Long rightResult = expressionCalculator.caclFromRPN(expressionCalculator.changeIdentifyToValue(expressionCalculator.toRPN(right), valueMap));
        logger.debug(dataCheckSetting.getChName() + " rightResult:" + rightResult);
        String predict = findPredict(tokens);
        logger.debug(dataCheckSetting.getChName() + " predict symbol :" + predict);
        return isWright(leftResult, rightResult, predict);
    }

    public static Tuple<List<String>, List<String>> cutToTwo(List<String> expression) throws Exception {
        int idx = expression.indexOf(">");
        if (idx == -1)
            idx = expression.indexOf("<");
        if (idx == -1)
            idx = expression.indexOf("<=");
        if (idx == -1)
            idx = expression.indexOf(">=");
        if (idx == -1)
            idx = expression.indexOf("=");
        if (idx > 0) {
            return new Tuple<>(expression.subList(0, idx), expression.subList(idx + 1, expression.size()));
        } else {
            throw new Exception("对不起，你的表达式里面没有 关系操作符：<,>,<,=");
        }
    }

    public static String findPredict(List<String> expression) throws Exception {

        int idx = expression.indexOf(">");
        if (idx == -1)
            idx = expression.indexOf("<");
        if (idx == -1)
            idx = expression.indexOf("<=");
        if (idx == -1)
            idx = expression.indexOf(">=");
        if (idx == -1)
            idx = expression.indexOf("=");
        if (idx > 0) {
            return expression.get(idx);
        } else {
            throw new Exception("对不起，你的表达式里面没有 关系操作符：<,>,<,=");
        }
    }

    public static boolean isWright(Long left, Long right, String predict) {
        if (predict.equals("<")) {
            return left < right;
        } else if (predict.equals("<="))
            return left <= right;
        else if (predict.equals(">="))
            return left >= right;
        else if (predict.equals(">"))
            return left > right;
        else if (predict.equals("="))
            return left.equals(right);
        else
            throw new UnsupportedOperationException("对不起，这个不支持< <= > >= == 意外的比较");
    }

}
