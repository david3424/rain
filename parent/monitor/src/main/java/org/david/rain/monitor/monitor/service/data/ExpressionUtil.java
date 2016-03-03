package org.david.rain.monitor.monitor.service.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by czw on 14-2-26.
 */
public class ExpressionUtil {
    public static boolean isSimpleSymbol(char x) {
        return SUPPORT_SIMPLE_SYMBOLS.contains(x);
    }

    public static boolean isSpace(char x) {
        return x == ' ' || x == '\t' || x == '\n' || x == '\r';
    }


    public static final Set<Character> SUPPORT_SIMPLE_SYMBOLS = new HashSet<>();

    static {
        SUPPORT_SIMPLE_SYMBOLS.add('(');
        SUPPORT_SIMPLE_SYMBOLS.add(')');
        SUPPORT_SIMPLE_SYMBOLS.add('*');
        SUPPORT_SIMPLE_SYMBOLS.add('/');
        SUPPORT_SIMPLE_SYMBOLS.add('+');
        SUPPORT_SIMPLE_SYMBOLS.add('-');
        SUPPORT_SIMPLE_SYMBOLS.add('=');
    }


    public static boolean isNumber(String token) {
        for (Character character : token.toCharArray()) {
            if (!Character.isDigit(character))
                return false;
        }
        return true;
    }


    public static boolean isCalcSymbol(String token) {
        return SUPPORT_CALC_SYMBOLS.contains(token);
    }

    public static boolean isCalcExpSymbol(String token) {
        return CACL_EXP_SYMBOLS.contains(token);
    }

    public static boolean isIdentifier(String token) {
        return !(isNumber(token) || isCalcExpSymbol(token));
    }

    public static final Set<String> SUPPORT_CALC_SYMBOLS = new HashSet<>();

    static {
        SUPPORT_CALC_SYMBOLS.add("*");
        SUPPORT_CALC_SYMBOLS.add("/");
        SUPPORT_CALC_SYMBOLS.add("+");
        SUPPORT_CALC_SYMBOLS.add("-");
    }

    public static final Set<String> CACL_EXP_SYMBOLS = new HashSet<>();

    static {
        CACL_EXP_SYMBOLS.add("*");
        CACL_EXP_SYMBOLS.add("/");
        CACL_EXP_SYMBOLS.add("+");
        CACL_EXP_SYMBOLS.add("-");
        CACL_EXP_SYMBOLS.add("(");
        CACL_EXP_SYMBOLS.add(")");
    }


    public static final Map<String, Integer> PRIORITY_MAP = new HashMap<>();

    static {
        PRIORITY_MAP.put("+", 1);
        PRIORITY_MAP.put("-", 1);
        PRIORITY_MAP.put("*", 2);
        PRIORITY_MAP.put("/", 2);
        PRIORITY_MAP.put("#", 0);
    }


    public static final Set<String> CHECK_ATTR_SYMBOLS = new HashSet<>();

    static {
        CHECK_ATTR_SYMBOLS.add("(");
        CHECK_ATTR_SYMBOLS.add(")");
        CHECK_ATTR_SYMBOLS.add("*");
        CHECK_ATTR_SYMBOLS.add("/");
        CHECK_ATTR_SYMBOLS.add("+");
        CHECK_ATTR_SYMBOLS.add("-");
        CHECK_ATTR_SYMBOLS.add("=");
        CHECK_ATTR_SYMBOLS.add(">");
        CHECK_ATTR_SYMBOLS.add("<");
        CHECK_ATTR_SYMBOLS.add("<=");
        CHECK_ATTR_SYMBOLS.add(">=");
    }
}
