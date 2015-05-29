package com.david.web.wanmei.repository;


import com.david.web.wanmei.entity.HdTable;
import com.david.web.wanmei.entity.ThisIgnore;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SqlResolver {
    private Object object;
    private String table;
    private List<String> paramNames;
    private List<String> ignoreParamNames;
    private List<Object> paramValues;

    public SqlResolver(Object object) {
        this.object = object;

        DaoValidater.assertHasHdTable(object.getClass());
        table = object.getClass().getAnnotation(HdTable.class).value();

//        ThisIgnore.class = object.getClass().getAnnotation(ThisIgnore.class).value();
        ignoreParamNames = new ArrayList<String>();
        Class c = object.getClass();
        Field[] fArr = c.getDeclaredFields();
        for (Field f : fArr) {
            if (f.isAnnotationPresent(ThisIgnore.class)) {
                ignoreParamNames.add(f.getName());
            }
        }

        initFieldAndValue();
    }

    /**
     *
     */
    private void initFieldAndValue() {
        Map map = null;
        try {
            map = PropertyUtils.describe(object);
            map.remove("class");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator iterator = map.entrySet().iterator();
        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (ignoreParamNames.contains(entry.getKey())) {
//                System.out.println("rmkey:" + entry.getKey() + ",rmvalue:" + entry.getValue());
                continue;
            }
//            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
            paramNames.add((String) entry.getKey());
            paramValues.add(entry.getValue());
        }

    }

    public String insertSql() {
        StringBuffer sql = new StringBuffer("INSERT INTO " + table + "(");
        StringBuffer prepared = new StringBuffer();
        for (int i = 0; i < paramNames.size(); i++) {
            if ("id".equals(paramNames.get(i))) {
                paramValues.remove(i);
                continue;
            }
            if (prepared.length() == 0) {
                sql.append(paramNames.get(i).toLowerCase());
                prepared.append("?");
            } else {
                sql.append(",").append(paramNames.get(i).toLowerCase());
                prepared.append(",?");
            }
        }
        sql.append(") VALUES (").append(prepared).append(")");
        return sql.toString();
    }

    public String updateSql() {
        return null;
    }

    public Object[] paramValues() {
        return paramValues.toArray();
    }
}
