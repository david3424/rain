package org.david.rain.beta.util;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 2014/10/30.
 */
public class Condition {
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final char PREFIX_MATCH = 'P';
    public static final char SUFFIX_MATCH = 'S';
    public static final char GLOBAL_MATCH = 'G';
    public static final char LIKE_MODE = 'L';
    public static final char STANDARD_MODE = 0;

    List<Integer> varTypesList = new ArrayList();
    private String conditionClauseStr = "";
    private String relateOperate = AND;
    public Condition(ConditionDef def, Map valueMap) {
        this(def, valueMap, AND);
    }
    public Condition(ConditionDef def, Map valueMap, String relateOperate) {
        this.relateOperate = relateOperate;
        String[] varNameArr = def.getConditionVarNames();
        List<String> usedSubConditionClauseList = new ArrayList();

        for (String varName : varNameArr) {
            if (!StringUtils.isEmpty((String) valueMap.get(varName))) {
                usedSubConditionClauseList.add(def.getSubConditionClause(varName));
                Object priValue =  valueMap.get(varName);
                if (def.isExistClassTypeInfo(varName)) {
                    Class targetClass = def.getClassTypeInfo(varName);
                    Object newValue = null;
                    if (targetClass == java.sql.Date.class) {
                        newValue = java.sql.Date.valueOf((String)priValue);
                        valueMap.put(varName, newValue);
                    } else if (targetClass == java.sql.Timestamp.class) {
                        newValue = java.sql.Timestamp.valueOf((String)priValue);
                        valueMap.put(varName, newValue);
                    } else if (targetClass == java.sql.Time.class) {
                        newValue = java.sql.Time.valueOf((String)priValue);
                        valueMap.put(varName, newValue);
                    } else if (targetClass == java.util.List.class) {
                        List valueList=new ArrayList();
                        if (priValue.getClass().isArray()){
                            String[] valueArr=(String[])priValue;
                            for (String string : valueArr) {
                                valueList.add(string);
                            }
                        }else{
                            valueList.add(priValue);
                        }
                        valueMap.put(varName, valueList);
                    }
                }
                if (def.isExistMatchModeInfo(varName)) {
                    List<Character> matchModeList = def.getMatchModeInfo(varName);
                    if (matchModeList.size() == 1) {
                        if (matchModeList.get(0) == Condition.GLOBAL_MATCH) {
                            priValue = "%" + priValue + "%";
                        } else if (matchModeList.get(0) == Condition.PREFIX_MATCH) {
                            priValue = priValue + "%";
                        } else if (matchModeList.get(0) == Condition.SUFFIX_MATCH) {
                            priValue = "%" + priValue;
                        }
                        valueMap.put(varName , priValue);
                    } else {
                        for (char currMatchMode : matchModeList) {
                            if (currMatchMode == Condition.GLOBAL_MATCH) {
                                String newValue = "%" + priValue + "%";
                                valueMap.put(varName + "_globalMatch", newValue);
                            } else if (currMatchMode == Condition.PREFIX_MATCH) {
                                String newValue = priValue + "%";
                                valueMap.put(varName + "_prefixMatch", newValue);
                            } else if (currMatchMode == Condition.SUFFIX_MATCH) {
                                String newValue = "%" + priValue;
                                valueMap.put(varName + "_suffixMatch", newValue);
                            }
                        }
                    }
                }
            }
        }
        this.conditionClauseStr = StringUtils.join(usedSubConditionClauseList, relateOperate);

    }
    public String getConditionClause() {
        return this.conditionClauseStr;
    }
    public String getConditionClauseWithWhere() {
        return "".equals(conditionClauseStr)?"":" WHERE " + conditionClauseStr;
    }
    public String getConditionClauseWithStartRelateOperatorIfNeeded() {
        if(conditionClauseStr.trim().equals("")) {
            return "";
        }else {
            return this.relateOperate + " " + conditionClauseStr;
        }

    }
    public String getConditionClauseWithRelateOperatorAtStart() {
        return this.relateOperate + " " + conditionClauseStr;
    }
    public Integer[] getConditionVarTypes() {
        return varTypesList.toArray(new Integer[]{});
    }
}